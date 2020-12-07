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

import nsu.ui.Tests;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import nsu.ui.TestRepository;

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

	@RequestMapping("tests")
	public ModelAndView list() {
		ArrayList<Tests> tests = this.testRepository.findAll();
		return new ModelAndView("tests/list", "tests", tests);
	}

	@RequestMapping(value = "create", method = RequestMethod.GET)
	public ModelAndView createForm(@ModelAttribute ArrayList<String> questions, Tests test) {
		System.out.println(questions);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("tests/form");
		mav.addObject("test", test);
		mav.addObject("questions", questions);
		this.questions = new ArrayList<>();
		return mav;

		//return new ModelAndView("tests/form", "questions", questions);
	}

	@RequestMapping(value = "create", method = RequestMethod.POST)
	public ModelAndView create(@Valid Tests test, BindingResult result,
			RedirectAttributes redirect) throws SQLException {
		if (result.hasErrors()) {
			return new ModelAndView("tests/form", "formErrors", result.getAllErrors());
		}

		questions.add(test.getQuestion());

		ModelAndView mav = new ModelAndView();
		mav.setViewName("tests/form");
		mav.addObject("test", test);
		mav.addObject("questions", questions);
		return mav;

		//test = this.testRepository.save(test);
		//redirect.addFlashAttribute("globalMessage", "Successfully created a new test");
		//return new ModelAndView("redirect:/create", "listOfTests", listOfTests);
	}

	@RequestMapping("foo")
	public String foo() {
		throw new RuntimeException("Expected exception in controller");
	}

}
