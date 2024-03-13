package com.moyeo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
@RequestMapping(value = "/")
@RequiredArgsConstructor
public class indexController {
	

	@RequestMapping(value = "/event", method = RequestMethod.GET)
	public String EventList(Model model) {
		return "event/mo_event";
	}
	
	@RequestMapping(value = "/cart", method = RequestMethod.GET)
	public String cartList(Model model) {
		return "cart/mo_cart";
	}
	
}