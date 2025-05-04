package net.orekyuu.fansite.web.presentation;

import net.orekyuu.fansite.web.usecase.IdentityUseCase;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/account/registration")
public record AccountRegistrationController(
        IdentityUseCase identityUseCase
) {

  @PostMapping
  public String registration(@RequestParam String accountId, @RequestParam String email) {
    try {
      identityUseCase.register(accountId, email);
    } catch (IdentityUseCase.AccountAlreadyRegisteredException e) {
      return "redirect:/?error=already_registered";
    }
    return "redirect:/";
  }
}
