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

import java.util.ArrayList;

/**
 * @author Rob Winch
 */
@Controller
@RequestMapping("/")
public class TestController {
	private final TestRepository testRepository;

	@Autowired
	public TestController(TestRepository testRepository) {
		this.testRepository = testRepository;
	}

	@RequestMapping
	public ModelAndView list() {
		ArrayList<Tests> tests = this.testRepository.findAll();
		return new ModelAndView("tests/list", "tests", tests);
	}

	@RequestMapping("{id}")
	public ModelAndView view(@PathVariable("id") Tests test) {
		return new ModelAndView("tests/view", "test", test);
	}

	@RequestMapping(params = "form", method = RequestMethod.GET)
	public String createForm(@ModelAttribute Tests test) {
		return "tests/form";
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView create(@Valid Tests test, BindingResult result,
			RedirectAttributes redirect) {
		if (result.hasErrors()) {
			return new ModelAndView("tests/form", "formErrors", result.getAllErrors());
		}
		test = this.testRepository.save(test);
		redirect.addFlashAttribute("globalMessage", "Successfully created a new test");
		return new ModelAndView("redirect:/{test.id}", "test.id", test.getId());
	}

	@RequestMapping("foo")
	public String foo() {
		throw new RuntimeException("Expected exception in controller");
	}

}
