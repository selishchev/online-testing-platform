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

	@RequestMapping("tests")
	public ModelAndView list() {
		ArrayList<Tests> tests = this.testRepository.findAll();
		return new ModelAndView("tests/list", "tests", tests);
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
	@RequestMapping(value = "removeQuestion", method = RequestMethod.POST)
	public ModelAndView removeQuestion(@ModelAttribute RemoveQuestionRequest req, BindingResult result,
									RedirectAttributes redirect) throws SQLException {
		DatabaseRepository.removeQuestion(req.getQuestion_id());
		return new ModelAndView("redirect:/create/" + req.getTest_id());
	}

	@RequestMapping("foo")
	public String foo() {
		throw new RuntimeException("Expected exception in controller");
	}

}
