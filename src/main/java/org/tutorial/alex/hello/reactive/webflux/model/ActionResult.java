package org.tutorial.alex.hello.reactive.webflux.model;

/**
 * @author yyHuangfu
 * @create 2024/10/7
 */

public class ActionResult {

    public ActionResult() {}

    public ActionResult(User userRes) {
        this.userRes = userRes;
    }

    public ActionResult(String message){
        this.message = message;
    }

    private String message;
    private User userRes;

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public User getUserRes() {
        return userRes;
    }
    public void setUserRes(User userRes) {
        this.userRes = userRes;
    }
}
