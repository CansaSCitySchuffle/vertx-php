package com.blankstyle.vertx.php.http;

import com.blankstyle.vertx.php.Handler;
import com.blankstyle.vertx.php.streams.ExceptionSupport;
import com.blankstyle.vertx.php.streams.WriteStream;
import com.caucho.quercus.annotation.Optional;
import com.caucho.quercus.env.BooleanValue;
import com.caucho.quercus.env.Callback;
import com.caucho.quercus.env.Env;
import com.caucho.quercus.env.StringValue;
import com.caucho.quercus.env.NumberValue;
import com.caucho.quercus.env.Value;

/**
 * A PHP compatible implementation of the Vert.x HttpClientRequest.
 *
 * @author Jordan Halterman
 */
public class HttpClientRequest implements WriteStream<HttpClientRequest>, ExceptionSupport<HttpClientRequest> {

  private org.vertx.java.core.http.HttpClientRequest request;

  public HttpClientRequest(org.vertx.java.core.http.HttpClientRequest request) {
    this.request = request;
  }

  public HttpClientRequest(Env env, org.vertx.java.core.http.HttpClientRequest request) {
    this.request = request;
  }

  /**
   * Returns request headers.
   */
  public Value headers(Env env) {
    return env.wrapJava(request.headers());
  }

  /**
   * Puts an HTTP header.
   */
  public Value putHeader(Env env, StringValue name, Value value) {
    request.putHeader(name.toString(), value.toString());
    return env.wrapJava(this);
  }

  /**
   * Forces the head of the request to be written before end.
   */
  public Value sendHead(Env env) {
    request.sendHead();
    return env.wrapJava(this);
  }

  @Override
  public HttpClientRequest write(Env env, Value data, @Optional StringValue enc) {
    if (enc != null && !enc.isDefault()) {
      request.write(data.toString(), enc.toString());
    }
    else {
      request.write(data.toString());
    }
    return this;
  }

  public HttpClientRequest continueHandler(Env env, Callback handler) {
    request.continueHandler(new Handler<Void>(env, handler));
    return this;
  }

  public void end() {
    request.end();
  }

  public void end(Env env) {
    end();
  }

  public void end(Env env, Value data) {
    request.end(data.toString());
  }

  @Override
  public HttpClientRequest drainHandler(Env env, Callback handler) {
    request.drainHandler(new Handler<Void>(env, handler));
    return this;
  }

  public HttpClientRequest setTimeout(Env env, Value timeoutMs) {
    request.setTimeout(timeoutMs.toInt());
    return this;
  }

  public HttpClientRequest setChunked(Env env, BooleanValue chunked) {
    request.setChunked(chunked.toBoolean());
    return this;
  }

  public BooleanValue isChunked(Env env) {
    return BooleanValue.create(request.isChunked());
  }

  @Override
  public HttpClientRequest setWriteQueueMaxSize(Env env, NumberValue size) {
    request.setWriteQueueMaxSize(size.toInt());
    return this;
  }

  @Override
  public BooleanValue writeQueueFull(Env env) {
    return BooleanValue.create(request.writeQueueFull());
  }

  @Override
  public HttpClientRequest exceptionHandler(Env env, Callback handler) {
    request.exceptionHandler(new Handler<Throwable>(env, handler));
    return this;
  }

}
