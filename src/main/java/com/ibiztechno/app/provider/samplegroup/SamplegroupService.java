package com.ibiztechno.app.provider.samplegroup;

import java.util.List;
import java.util.Map;import org.springframework.web.multipart.MultipartFile;

import com.ibiztechno.app.model.CodeName;
import com.ibiztechno.app.model.ListType;
import com.ibiztechno.app.model.ReportView;
import com.ibiztechno.app.model.TreeType;
import com.ibiztechno.app.model.TreeView;
import com.ibiztechno.app.model.FormType;

public interface SamplegroupService {
	public List<FormType> getForms(String lantype,String userId);
	 
	public List<ListType> getListType(String lanType, String listCode);
	public List<ReportView> getReportType(String reportCode);
//	action
	public List<CodeName> getActions(String lanType,String userId);
	public List<CodeName> getLinks(String lanType,String userId);
	public List<CodeName> getEnquiries(String lanType,String userId);
	public List<CodeName> getImports(String lanType,String userId);
	public List<CodeName> getExports(String lanType,String userId);
	public List<CodeName> getMaintenances(String lanType,String userId);
	public List<CodeName> getReports(String lanType,String userId);
//	search
	public List<CodeName> getFilters(String lanType,String userId);
	public List<CodeName> getGroupBys(String lanType,String userId);
	public List<CodeName> getFavorites(String lanType,String userId);
    
	public List<TreeView> getTrees(TreeType model);
	public List<Map<String, Object>> getList(ListType model);
	public List<Map<String, Object>> getAuthorizeList(ListType model);

	public List<Map<Object, Object>> getListView(ListType model);
	
	public String verifyPermission(String typeId,String userId, String remoteAddr);
	
	public List<SamplegroupView> getDetail(String id);
	public Long create(Samplegroup model);
	public Long update(SamplegroupView model);
	public Long delete(SamplegroupView model);
	public Long delete(String id, String userId, String remoteAddr);
	public Long authorize(SamplegroupView model);
	public Long uploadFile(Samplegroup model,MultipartFile file);
	public Long upload(ListType model);
	public Long importData(ListType model);
}
