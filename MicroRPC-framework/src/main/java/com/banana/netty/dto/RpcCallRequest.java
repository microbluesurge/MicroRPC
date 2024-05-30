package com.banana.netty.dto;

import lombok.*;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@ToString
@Builder
@Data
public class RpcCallRequest {

    String interfaceName;

    String methodName;

    Class<?>[] paramTypes ;

    Object[] params;



    public int calculate(String s) {
        Pattern p = Pattern.compile("\\+|-|\\d+|\\(|\\)");
        Matcher matcher = p.matcher(s);
        Deque<String> expQueue = new LinkedList<>();
        Deque<String> signQueue = new LinkedList<>();
        String last = "(";
        while (matcher.find()) {
            String str = matcher.group();
            switch (str) {
                case "(" :
                    signQueue.addLast(str);
                    break;
                case ")" :
                    while (!signQueue.peekLast().equals("(")) {
                        expQueue.addLast(signQueue.removeLast());
                    }
                    signQueue.removeLast();
                    break;
                case "+" :
                case "-" :
                    if(last.equals("(")) {
                        expQueue.addLast("0");
                    }
                    while (!signQueue.isEmpty() && (signQueue.peekLast().equals("+")
                            || signQueue.peekLast().equals("-"))) {
                        expQueue.addLast(signQueue.removeLast());
                    }
                    signQueue.addLast(str);
                    break;
                default :
                    expQueue.addLast(str);
            }
            last = str;
        }
        while (!signQueue.isEmpty()) {
            expQueue.addLast(signQueue.removeLast());
        }
        Deque<Integer> ans = new LinkedList<>();
        while (!expQueue.isEmpty()) {
            String temp = expQueue.pollFirst();
            if(temp.equals("+")) {
                ans.addLast(ans.pollLast() + ans.pollLast());
            } else if(temp.equals("-")) {
                int b = ans.pollLast();
                int a = ans.isEmpty() ? 0 : ans.pollLast();
                ans.addLast(a - b);
            } else {
                ans.addLast(Integer.valueOf(temp));
            }
        }
        return ans.pollLast();
    }
}
