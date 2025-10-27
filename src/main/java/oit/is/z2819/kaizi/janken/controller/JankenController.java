package oit.is.z2819.kaizi.janken.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import oit.is.z2819.kaizi.janken.model.Entry;
import oit.is.z2819.kaizi.janken.model.MatchMapper;
import oit.is.z2819.kaizi.janken.model.User;
import oit.is.z2819.kaizi.janken.model.UserMapper;

@Controller
public class JankenController {

  @Autowired
  private Entry entry;

  @Autowired
  private UserMapper user;

  @Autowired
  private MatchMapper matches;

  @GetMapping("/janken")
  public String jankenHand(@RequestParam(required = false) String hand, ModelMap model, Principal prin) {
    model.addAttribute("username", prin.getName());
    entry.addUser(prin.getName());
    model.addAttribute("room", entry);
    model.addAttribute("users", user.selectAllUsers());
    model.addAttribute("matches", matches.selectAllMatches());

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

  @GetMapping("/match")
  String match(@RequestParam Integer id, ModelMap model, Principal prin) {
    model.addAttribute("opponent_id", user.selectUserById(id).getId());
    model.addAttribute("user_name", prin.getName());
    model.addAttribute("opponent_name", user.selectUserById(id).getName());
    return "match.html";
  }

  @GetMapping("/fight")
  String fight(@RequestParam String hand, @RequestParam String user_name, @RequestParam Integer opponent_id,
      ModelMap model, Principal prin) {
    model.addAttribute("user_name", user_name);
    model.addAttribute("opponent_id", opponent_id);
    model.addAttribute("opponent_name", user.selectUserById(opponent_id).getName());
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

    matches.insertMatch(user.selectUserByName(user_name).getId(), opponent_id, hand, cpuHand);

    return "match.html";
  }
}
