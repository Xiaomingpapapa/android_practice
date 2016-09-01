package com.example.myapplication;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xunfang.experiment.dooraccess.bean.CompanyBean;
import com.xunfang.experiment.dooraccess.db.CompanyDBUtil;
import com.xunfang.experiment.dooraccess.util.MyConfig;

/**
 * <p>Title���Ž�ϵͳ</p>
 * <p>Description����ҵ�������</p>
 * <p>Company��������Ѷ��ͨ�ż������޹�˾ </p>
 * <p>Copyright: Copyright (c) 2012 </p>
 * @version 1.0.0.0
 * @author sas 
 */
public class CompanyActivity extends Activity implements OnClickListener{
	
	private final int DIALOG_ID = 17;
	//����ؼ�
	private ImageView revert;//����"��ť"
	private TextView title;//����
	private TextView companynamestr;//��ҵ��(�ַ�)
	private EditText companyname;//��ҵ��
	private Button save_btn;//���水ť
	private Button cancel_btn;//ȡ��ť
	
	private CompanyBean companyBean;
	private String operate = "select";//����(��ӻ�༭)
	private String company_name;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.company);
        //�Զ��崰����ʽ
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.common_title);
		revert = (ImageView)findViewById(R.id.common_title_revert);
		title = (TextView)findViewById(R.id.common_title_title);
		title.setText(getText(R.string.companyMgr)+"����"+getText(R.string.companyMgr));
		companynamestr = (TextView)findViewById(R.id.company_namestr);
		companyname = (EditText)findViewById(R.id.company_name);
		save_btn = (Button)findViewById(R.id.company_operate_save);
		cancel_btn = (Button)findViewById(R.id.company_operate_cancel);
		revert.setOnClickListener(this);
		companynamestr.setText(getText(R.string.companynamestr)+"��");
		save_btn.setOnClickListener(this);
		cancel_btn.setOnClickListener(this);
    }
    @Override
    protected void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    	if("".equals(MyConfig.getNOW_LOGINNAME())){
    		//Intent intent = new Intent(CompanyActivity.this,LoginActivity.class);
    		Intent intent = new Intent(CompanyActivity.this,Main.class);
			Main.setChecked(R.id.main_tab_login);
    		startActivity(intent);
    		finish();
    	}else{
    		operate = "select";
        	new OperateTask().execute(operate);
    	}
    }
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.company_operate_save:
			company_name = companyname.getText().toString();
			System.out.println("operate--->"+operate);
			if("add".equals(operate)){
				System.out.println("add");
				if(company_name == null || "".equals(company_name)){
					Toast.makeText(CompanyActivity.this, getText(R.string.companyname_empty), Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(CompanyActivity.this,"保存成功",Toast.LENGTH_SHORT).show();
					new OperateTask().execute(operate);
				}
			}else if("edit".equals(operate)){
				System.out.println("edit");
				if("".equals(company_name)){
					Toast.makeText(CompanyActivity.this, getText(R.string.companyname_empty), Toast.LENGTH_SHORT).show();
				}else{
					new OperateTask().execute(operate);
				}
			}
			break;
		case R.id.common_title_revert:
		case R.id.company_operate_cancel:
			//startActivity(new Intent(CompanyActivity.this,Main_bak.class));
			finish();
			break;
		default:
			break;
		}
	}
	/**
	 * �첽����������ӻ�༭��ҵ��Ϣ
	 * @author sas
	 *
	 */
	class OperateTask extends AsyncTask<String, String, String>{

		/**
		 * ��ִ̨����ӻ�༭����Ա����
		 */
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			boolean flag = false;
			CompanyDBUtil companyDBUtil = new CompanyDBUtil(CompanyActivity.this);
			if("add".equals(params[0])){
				ContentValues values = new ContentValues();
				values.put("name", company_name);
				flag = companyDBUtil.addCompany(values);
			}else if("edit".equals(params[0])){
				flag = companyDBUtil.updateCompany(company_name, companyBean.getId());
			}else if("select".equals(params[0])){
				System.out.println("��ѯ��ҵ��Ϣ");
				companyBean = companyDBUtil.getCompanyBean();
				if(companyBean == null){
					System.out.println("��ҵ��ϢΪ��");
					flag = false;
				}else{
					System.out.println("�õ���ҵ��Ϣ");
					flag = true;
					//operate = "edit";
				}
			}
			return flag+"";
		}
		/**
		 * ִ��doInBackgroundǰ������
		 */
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			showDialog(DIALOG_ID);
		}
		/**
		 * ִ��doInBackground�󱻵���
		 */
		@Override
		protected void onPostExecute(String result){
			// TODO Auto-generated method stub
			dismissDialog(DIALOG_ID);
			if("true".equals(result)){
				if("select".equals(operate)){
					companyname.setText(companyBean.getCompanyname());
					operate = "edit";
				}else if("add".equals(operate)){
					operate = "edit";
					Toast.makeText(CompanyActivity.this, getText(R.string.operate_success), Toast.LENGTH_SHORT).show();
					new OperateTask().execute("select");
				}else if("edit".equals(operate)){
					Toast.makeText(CompanyActivity.this, getText(R.string.operate_success), Toast.LENGTH_SHORT).show();
					operate = "edit";
				}
			}else if("false".equals(result)){
				if(!"select".equals(operate)){	
					Toast.makeText(CompanyActivity.this, getText(R.string.operate_failure), Toast.LENGTH_SHORT).show();
				}else{
					operate = "add";
				}
			}
		}
	}
	/**
	 * ����ؼ�
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// �жϷ��ؼ��Ƿ񱻰���
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			//startActivity(new Intent(CompanyActivity.this,Main_bak.class));
			finish();
		}
		return false;
	}
	@Override
	protected Dialog onCreateDialog(int id) {
		// TODO Auto-generated method stub
		switch (id) {
		case DIALOG_ID:
			ProgressDialog dialog = new ProgressDialog(CompanyActivity.this); 
			dialog.setMessage(getText(R.string.operating)); 
			dialog.setCanceledOnTouchOutside(false);// ���õ����ĻDialog����ʧ
			return dialog; 
		default:
			return null;
		}
	}
}