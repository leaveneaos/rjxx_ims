package com.rjxx.taxeasy.configuration;


import com.dingtalk.open.client.api.model.corp.MessageBody;

public class MessageDelivery {
	
	public String msgType;
	public MessageBody message;

	public MessageDelivery() {
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public MessageBody getMessage() {
		return message;
	}

	public void setMessage(MessageBody message) {
		this.message = message;
	}

	public MessageDelivery withMessage(String msgType, MessageBody msg) {
		this.msgType = msgType;
		this.message = msg;
		return this;
	}
}
