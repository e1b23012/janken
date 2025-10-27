package oit.is.z2819.kaizi.janken.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import oit.is.z2819.kaizi.janken.model.Entry;
import oit.is.z2819.kaizi.janken.model.User;
import oit.is.z2819.kaizi.janken.model.UserMapper;

@Controller
public class JankenController {

  @Autowired
  private Entry entry;

  @Autowired
  private UserMapper user;

  @GetMapping("/janken")
  public String jankenHand(@RequestParam(required = false) String hand, ModelMap model, Principal prin) {
    model.addAttribute("username", prin.getName());
    entry.addUser(prin.getName());
    model.addAttribute("room", entry);
    model.addAttribute("users", user.selectAllUsers());
    if (hand == null) {
      return "janken.html";
    }
    model.addAttribute("playerHand", hand);
    String cpuHand = "gu";
    model.addAttribute("cpuHand", cpuHand);

    String result = "";
    if (hand.equals(cpuHand)) {
      result = "引き分け";
    } else if (hand.equals("gu")) {
      if (cpuHand.equals("choki")) {
        result = "勝ち";
      } else {
        result = "負け";
      }

    } else if (hand.equals("choki")) {
      if (cpuHand.equals("pa")) {
        result = "勝ち";
      } else {
        result = "負け";
      }
    } else if (hand.equals("pa")) {
      if (cpuHand.equals("gu")) {
        result = "勝ち";
      } else {
        result = "負け";
      }
    }
    model.addAttribute("result", result);

    return "janken.html";
  }

}
