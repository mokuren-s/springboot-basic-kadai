package com.example.springkadaiform.controller;

import org.springframework.core.Conventions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.springkadaiform.form.ContactForm;

@Controller
public class ContactFormController {
	
	@GetMapping("/form")
	public String form(Model model) {
		// 初期表示としてフォームの中身を空にする
		if (!model.containsAttribute("contactForm")) {
			model.addAttribute("contactForm",new ContactForm());	
		}
		
		return "contactFormView";
	}
	
	@PostMapping("/confirm")
	public String confirm(RedirectAttributes redirectAttributes,
			              @ModelAttribute@Validated ContactForm form,
			              BindingResult result) {
		// バリデーションエラーがあったら
        if (result.hasErrors()) {
        	// もう一度フォームクラスをビューに受け渡しつつ、
        	redirectAttributes.addFlashAttribute("contactForm",form);
        	// バリデーション結果をビューに受け渡す
        	redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX           
					+Conventions.getVariableName(form), result);
            // リダイレクトして再表示（エラー表示あり）
            return "redirect:/form";
        }
         // エラーが無ければ確認画面を表示
        return "confirmView";
	}
}