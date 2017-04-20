package com.rjxx.taxeasy.configuration;

public class Message {
     private String touser;
     private String toparty;
     private String agentid;
     private String code;
     private String msgType;
     private text text;
     
     public String getTouser() {
		return touser;
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}

	public String getToparty() {
		return toparty;
	}

	public void setToparty(String toparty) {
		this.toparty = toparty;
	}

	public String getAgentid() {
		return agentid;
	}

	public void setAgentid(String agentid) {
		this.agentid = agentid;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}



	public text getText() {
		return text;
	}

	public void setText(text text) {
		this.text = text;
	}



	public static class text{
    	 private String content;

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}
    	 
     }
}
