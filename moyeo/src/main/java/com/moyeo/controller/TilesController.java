package com.moyeo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class TilesController {
	@RequestMapping("/")
	public String tiles() {
		return "index";
	}
	
	@RequestMapping("/tiles1")
	public String tiles1() {
		return "layout/tiles1";
	}
	
	@RequestMapping("/tiles2")
	public String tiles2() {
		return "layout/sub/tiles2";
	}
	
	@RequestMapping("/admin")
	public String admin() {
		return "admin";
	}
	
	@RequestMapping("/admin1")
	public String admin1() {	
		return "admin/display1";
	}
	
	@RequestMapping("/admin2")
	public String admin2() {
		return "admin/sub/display2";
	}
}
