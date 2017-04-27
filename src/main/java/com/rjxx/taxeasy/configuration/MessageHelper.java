package com.rjxx.taxeasy.configuration;
import com.dingtalk.open.client.ServiceFactory;
import com.dingtalk.open.client.api.model.corp.MessageSendResult;
import com.dingtalk.open.client.api.service.corp.MessageService;

public class MessageHelper {

	public static class Receipt {
		String invaliduser;
		String invalidparty;
		String errcode;
		String errmsg;

		public String getInvaliduser() {
			return invaliduser;
		}

		public void setInvaliduser(String invaliduser) {
			this.invaliduser = invaliduser;
		}

		public String getInvalidparty() {
			return invalidparty;
		}

		public void setInvalidparty(String invalidparty) {
			this.invalidparty = invalidparty;
		}

		public String getErrcode() {
			return errcode;
		}

		public void setErrcode(String errcode) {
			this.errcode = errcode;
		}

		public String getErrmsg() {
			return errmsg;
		}

		public void setErrmsg(String errmsg) {
			this.errmsg = errmsg;
		}
	}
	
	public static Receipt send(String accessToken, LightAppMessageDelivery delivery) 
			throws Exception {
		MessageService messageService = ServiceFactory.getInstance().getOpenService(MessageService.class);
		MessageSendResult reulst = messageService.sendToCorpConversation(accessToken, delivery.touser,
				delivery.toparty, delivery.agentid, delivery.msgType, delivery.message);
		Receipt receipt = new Receipt();
		receipt.invaliduser = reulst.getInvaliduser();
		receipt.invalidparty = reulst.getInvalidparty();

		return receipt;
		}
	
	
	public static String send(String accessToken, ConversationMessageDelivery delivery) 
		throws Exception {
		MessageService messageService = ServiceFactory.getInstance().getOpenService(MessageService.class);
		return  messageService.sendToNormalConversation(accessToken, delivery.sender, 
				delivery.cid, delivery.msgType, delivery.message);
	}
}
