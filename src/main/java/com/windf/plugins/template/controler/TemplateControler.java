package com.windf.plugins.template.controler;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@Scope("prototype")
@RequestMapping(value = {"/u", "/d"})
public class TemplateControler {
	@RequestMapping(value = "{path1}/{path2}", method = {RequestMethod.GET})
	public String index(@PathVariable String path1, @PathVariable String path2) {
		String template = "template/" + path1 + "_" + path2;
		return template;
	}
}
