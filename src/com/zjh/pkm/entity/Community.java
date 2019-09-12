package com.zjh.pkm.entity;

public class Community {
	
	private int commno;
	private String commname;
	private String filepath;
	private String region1;
	private String region2;
	private String region3;
	private String region4;
	private String region5;
	private String region6;
	private String region7;
	private String region8;
	private String region9;
	private String region10;
	
	public int getCommno() {
		return commno;
	}
	public void setCommno(int commno) {
		this.commno = commno;
	}
	public String getCommname() {
		return commname;
	}
	public void setCommname(String commname) {
		this.commname = commname;
	}
	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	//设置Region中的值
	public void setRegion(int n,String pkn){
		switch(n){
			case 1:this.region1=pkn;break;
			case 2:this.region2=pkn;break;
			case 3:this.region3=pkn;break;
			case 4:this.region4=pkn;break;
			case 5:this.region5=pkn;break;
			case 6:this.region6=pkn;break;
			case 7:this.region7=pkn;break;
			case 8:this.region8=pkn;break;
			case 9:this.region9=pkn;break;
			case 10:this.region10=pkn;break;
			default:System.out.println("Region序号只能为1到10");break;
		}
	}
	//获取Region中的值
	public String getRegion(int n) {
		switch(n){
		case 1:return this.region1;
		case 2:return this.region2;
		case 3:return this.region3;
		case 4:return this.region4;
		case 5:return this.region5;
		case 6:return this.region6;
		case 7:return this.region7;
		case 8:return this.region8;
		case 9:return this.region9;
		case 10:return this.region10;
		default:System.out.println("Region序号只能为1到10");return null;
		}
	}
	//获取Region1~Region10中的非null个数
	public int getNotNull(Community comm){
		int n = 0;
		for(int i=1;i<=10;i++){
			if(comm.getRegion(i) != null){
				n=n+1;
			}
		}
		return n;
	}
}
