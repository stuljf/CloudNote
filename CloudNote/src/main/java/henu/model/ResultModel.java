package henu.model;

public class ResultModel {
	
	private int status;
	private String msg;
	private Object data;
	
	private ResultModel(int status, String msg, Object data) {
		this.status=status;
		this.msg=msg;
		this.data=data;
	}
	
	public static ResultModel ok() {
		return new ResultModel(200, "ok", null);
	}
	
	public static ResultModel ok(Object data) {
		return new ResultModel(200, "ok", data);
	}
	
	public static ResultModel result(int status, String msg) {
		return new ResultModel(status, msg, null);
	}
	
	public static ResultModel result(int status, String msg, Object data) {
		return new ResultModel(status, msg, data);
	}	
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
}
