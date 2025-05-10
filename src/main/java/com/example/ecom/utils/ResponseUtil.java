package com.example.ecom.utils;


import com.example.ecom.auth.payload.response.Result;

public class ResponseUtil {
 public static Result success(String message, Object data) {
  return new Result(true, message, data);
 }
 public static Result error(String message) {
  return new Result(false, message, null);
 }

}
