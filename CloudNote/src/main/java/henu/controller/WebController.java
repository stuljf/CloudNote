package henu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/web")
public class WebController {

	@RequestMapping("/{uri}")
	public String page(@PathVariable String uri) {
		return uri.equals("")?"main":uri;
	}
}
