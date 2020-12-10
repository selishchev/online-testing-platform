/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package nsu.ui.mvc;

import javax.validation.Valid;

import nsu.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.springframework.web.servlet.view.RedirectView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Rob Winch
 */
@Controller
@RequestMapping("/")
public class TestController {
	private final TestRepository testRepository;
	private ArrayList<String> questions = new ArrayList<>();

	@Autowired
	public TestController(TestRepository testRepository) {
		this.testRepository = testRepository;
	}


	@RequestMapping("{id}/tests")
	public ModelAndView list(@PathVariable("id") Long id) {
		ArrayList<Tests> tests = new ArrayList<>();
		User user = DatabaseRepository.findUserById(id);
		if(user.getIsTeacher()) {
			tests = DatabaseRepository.findTeacherTests(id);
		}
		else {
			tests = this.testRepository.findAll();
		}

		ModelAndView mav = new ModelAndView();
		mav.addObject("tests", tests);
		mav.addObject("user", user);
		mav.setViewName("tests/list");
		return mav;
	}

	@RequestMapping(value = "tests/test", method = RequestMethod.GET)
	public ModelAndView createFormTest(@ModelAttribute Tests test) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("tests/test");
		mav.addObject("test", test);
		return mav;
	}
	@RequestMapping(value = "tests/test", method = RequestMethod.POST)
	public ModelAndView createTest(@ModelAttribute Tests test) {
		try {
			test = this.testRepository.save(test);
		}
		catch (Exception e){
			e.printStackTrace();
		}
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/create/" + test.getId());
		return mav;
	}

	@RequestMapping(value = "create/{id}", method = RequestMethod.GET)
	public ModelAndView createForm(@PathVariable("id") Long id) {

		ArrayList<Questions> questions = this.testRepository.findTest(id);
		Tests test = DatabaseRepository.getTestById(id);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("tests/form");
		mav.addObject("test", test);
		mav.addObject("questions", questions);
		return mav;

	}

	@RequestMapping(value = "addQuestion", method = RequestMethod.POST)
	public ModelAndView addQuestion(@Valid Tests test, BindingResult result,
			RedirectAttributes redirect) throws SQLException {
		DatabaseRepository.saveQuestion(test.getQuestion(), test.getId());
		return new ModelAndView("redirect:/create/" + test.getId());
	}

	@RequestMapping(value = "create/removeQuestion", params = {"testId", "questionId"}, method = RequestMethod.GET)
	public ModelAndView removeQuestion(@RequestParam(value = "testId") Long testId,
									   @RequestParam(value = "questionId") Long questionId) {

		DatabaseRepository.removeQuestion(questionId);
		return new ModelAndView("redirect:/create/" + testId);
	}

	@RequestMapping(value = "create/deleteTest", params = {"testId"}, method = RequestMethod.GET)
	public ModelAndView deleteTest(@RequestParam(value = "testId") Long testId){
		DatabaseRepository.deleteTest(testId);
		return new ModelAndView("redirect:/tests");
	}

	@RequestMapping(value = "registration", method = RequestMethod.GET)
	public ModelAndView registration(@ModelAttribute User user) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("tests/registration");
		mav.addObject("user", user);
		return mav;
	}

	@RequestMapping(value = "registration", method = RequestMethod.POST)
	public ModelAndView addUser(@ModelAttribute User user, @Valid User userForm, BindingResult result,
								RedirectAttributes redirect) {
		if (result.hasErrors()) {
			return new ModelAndView("/registration", "formErrors", result.getAllErrors());
		}
		boolean checker = DatabaseRepository.checkUser(user);
		if (checker) {
			return new ModelAndView("/registration","userError", "Пользователь с таким именем уже существует");
		}
		ModelAndView mav = new ModelAndView();
		User currentUser = DatabaseRepository.saveUser(user);
		mav.setViewName("redirect:/" + currentUser.getId() + "/tests");
		mav.addObject("user", user);
		return mav;
	}

	@RequestMapping(value = "login", method = RequestMethod.GET)
	public ModelAndView login(@ModelAttribute User user) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("tests/login");
		mav.addObject("user", user);
		return mav;
	}

	@RequestMapping(value = "login", method = RequestMethod.POST)
	public ModelAndView loginUser(@ModelAttribute User user, @Valid User loginForm, BindingResult result,
								RedirectAttributes redirect) {
		if (result.hasErrors()) {
			return new ModelAndView("/login", "formErrors", result.getAllErrors());
		}
		if (DatabaseRepository.checkUser(user)) {
			if (!DatabaseRepository.checkPassword(user)) {
				return new ModelAndView("/login","passwordError", "Incorrect password");
			}
			else {
				user = DatabaseRepository.findByEmail(user.getEmail());
				ModelAndView mav = new ModelAndView();
				mav.setViewName("redirect:/tests");
				mav.addObject("user", user);
				return mav;
			}
		}
		else {
			return new ModelAndView("tests/login","emailError", "Пользователя с таким именем не существует");
		}
	}

	@RequestMapping("foo")
	public String foo() {
		throw new RuntimeException("Expected exception in controller");
	}
}
