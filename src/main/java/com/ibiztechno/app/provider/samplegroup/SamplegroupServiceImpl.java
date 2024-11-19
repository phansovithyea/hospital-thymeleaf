package com.ibiztechno.app.provider.samplegroup;

import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.ibiztechno.app.model.CodeName;
import com.ibiztechno.app.model.ListType;
import com.ibiztechno.app.model.ReportView;
import com.ibiztechno.app.model.TreeType;
import com.ibiztechno.app.model.TreeView;
import com.ibiztechno.app.provider.home.Menu;
import com.ibiztechno.app.model.FormType;
import com.ibiztechno.app.repository.sql.SqlRepository;

@Repository
public class SamplegroupServiceImpl implements SamplegroupService {
	private static final Logger logger = LoggerFactory.getLogger(SamplegroupServiceImpl.class);
	
	@Autowired
    SqlRepository sqlRepository;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<FormType> getForms(String lanType,String userId) {
		try {
			return  (List<FormType>) (Object) sqlRepository.GetList("TemplateGroup_GetForm",
					new MapSqlParameterSource().addValue("userid", userId).addValue("LanType", lanType),
					FormType.class);
		}catch(Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ListType> getListType(String lanType, String listCode) {
		try {
			return (List<ListType>) (Object) sqlRepository.GetList("TemplateGroup_GetListType",
					new MapSqlParameterSource().addValue("ListCode", listCode).addValue("LanType",lanType), ListType.class);
			
		}catch(Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ReportView> getReportType(String reportCode) {
		try {
			return (List<ReportView>) (Object) sqlRepository.GetList("TemplateGroup_GetReportType",
					new MapSqlParameterSource().addValue("ReportCode", reportCode), ReportView.class);
		}catch(Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}
	
	
	@Override
	public List<CodeName> getActions(String lanType,String userId) {
		try {
			return sqlRepository.GetParam("TemplateGroup_GetActionList",
					new MapSqlParameterSource().addValue("UserId", userId).addValue("lantype",lanType));
		}catch(Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}
	
	@Override
	public List<CodeName> getLinks(String lanType,String userId) {
		try {
			return sqlRepository.GetParam("TemplateGroup_GetLinkList",
					new MapSqlParameterSource().addValue("UserId", userId).addValue("lantype", lanType));
		}catch(Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}
	
	@Override
	public List<CodeName> getEnquiries(String lanType,String userId) {
		try {
			return sqlRepository.GetParam("TemplateGroup_GetEnquiryList",
					new MapSqlParameterSource().addValue("UserId", userId).addValue("lantype",lanType));
		}catch(Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}
	
	@Override
	public List<CodeName> getImports(String lanType,String userId) {
		try {
			return sqlRepository.GetParam("TemplateGroup_GetImportList",
					new MapSqlParameterSource().addValue("UserId", userId).addValue("lantype", lanType));
		}catch(Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}
	
	@Override
	public List<CodeName> getExports(String lanType,String userId) {
		try {
			return sqlRepository.GetParam("TemplateGroup_GetExportList",
					new MapSqlParameterSource().addValue("UserId", userId).addValue("lantype", lanType));
		}catch(Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}
	
	@Override
	public List<CodeName> getMaintenances(String lanType,String userId) {
		try {
			return sqlRepository.GetParam("TemplateGroup_GetMainList",
					new MapSqlParameterSource().addValue("UserId", userId).addValue("lantype", lanType));
		}catch(Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}
	
	@Override
	public List<CodeName> getReports(String lanType,String userId) {
		try {
			return sqlRepository.GetParam("TemplateGroup_GetReportList",
					new MapSqlParameterSource().addValue("UserId", userId).addValue("lantype",lanType));
		}catch(Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}
	
	@Override
	public List<CodeName> getFilters(String lanType,String userId) {
		try {
			sqlRepository.GetParam("TemplateGroup_GetCriteriaList", 
					new MapSqlParameterSource().addValue("UserId", userId).addValue("lantype", lanType));
		}catch(Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}
	
	@Override
	public List<CodeName> getGroupBys(String lanType,String userId) {
		try {
			return sqlRepository.GetParam("TemplateGroup_GetTreeList", 
					new MapSqlParameterSource().addValue("UserId", userId).addValue("lantype", lanType));
		}catch(Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}
	
	@Override
	public List<CodeName> getFavorites(String lanType,String userId) {
		try {
			return sqlRepository.GetParam("TemplateGroup_GetUserCriteriaList", 
					new MapSqlParameterSource().addValue("UserId", userId).addValue("lantype", lanType));
		}catch(Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TreeView> getTrees(TreeType model) {
		try {
			 return (List<TreeView>) (Object)sqlRepository.GetParam("TemplateGroup_Treeview",
					new MapSqlParameterSource().addValue("UserId", model.getUserID()).addValue("lantype", model.getLanType())
					.addValue("TreeCode", "001").addValue("criteria", model.getCriteria()));
		}catch(Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}
	
	@Override
	public List<Map<String, Object>> getList(ListType model) {
		try {
			List<Map<String, Object>> listData = sqlRepository.GetList("TemplateGroup_GetList",
					new MapSqlParameterSource().addValue("lantype", model.getLanType())
							.addValue("criteria", model.getCriteria()).addValue("userid", model.getUserID())
							.addValue("orderby", model.getOrderBy()).addValue("pagenumber", model.getPageNumber()));

			return listData;
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}
	
	@Override
	public List<Map<String, Object>> getAuthorizeList(ListType model) {
		try {
			List<Map<String, Object>> listData = sqlRepository.GetList("TemplateGroup_GetAuthorizeList",
					new MapSqlParameterSource().addValue("lantype", model.getLanType())
							.addValue("criteria", model.getCriteria()).addValue("userid", model.getUserID())
							.addValue("orderby", model.getOrderBy()).addValue("pagenumber", model.getPageNumber()));

			return listData;
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}
	
	@Override
	public List<Map<Object, Object>> getListView(ListType model) {
		try {
			return sqlRepository.GetListObj("TemplateGroup_ListView",
					new MapSqlParameterSource().addValue("listcode", model.getListViewCode())
							.addValue("lantype", model.getLanType()).addValue("criteria", model.getCriteria())
							.addValue("userid", model.getUserID()).addValue("orderby", model.getOrderBy())
							.addValue("pagenumber", model.getPageNumber()));

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}
	
	@Override
	public String verifyPermission(String typeId, String userId, String remoteAddr) {
		try {
			return sqlRepository.GetString("TemplateGroup_VerifyPermission",
    			new MapSqlParameterSource().addValue("TypeID", typeId).addValue("UserID", userId)
    				.addValue("RemoteAddr", remoteAddr).addValue("retval", ""));
		} catch(Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SamplegroupView> getDetail(String id) {
		try {
			return (List<SamplegroupView>) (Object) sqlRepository.GetList("TemplateGroup_GetByID",
					new MapSqlParameterSource().addValue("id", id), SamplegroupView.class);
		}catch(Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}

//	@Override
	public Long create(Samplegroup model) {

		try {
			return sqlRepository.GetLong("TemplateGroup_Create", new MapSqlParameterSource()
					.addValue("GroupCode", model.getGroupCode())
					.addValue("GroupDesc", model.getGroupDesc())
					.addValue("RecStatus", model.isRecStatus() ? 1 : 0)
					.addValue("UserID", model.getUserID())
					.addValue("RemoteAddr",model.getRemoteAddr())
					.addValue("retval", 0));

		}catch(Exception e) {
			logger.error(e.getMessage());
		}
		return 0L;
	}

	@Override
	public Long update(SamplegroupView model) {
		try {
			return sqlRepository.GetLong("TemplateGroup_Update",
					new MapSqlParameterSource().addValue("GroupCode", model.getGroupCode())
//			    .addValue("TempShort", model.getTempShort())
							.addValue("GroupDesc", model.getGroupDesc())
//			    .addValue("GroupDescU", model.getGroupDescU())
//			    .addValue("TempOrder", model.getTempOrder())
//			    .addValue("TempRemark", model.getTempRemark())
//			    .addValue("TempPass", model.getTempPass())
//			    .addValue("TempDate", model.getTempDate())
							.addValue("RecStatus", model.isRecStatus() ? 1 : 0)
//			    .addValue("TempNumeric", model.getTempNumeric())
//			    .addValue("TempInt", model.getTempInt())
//			    .addValue("TempSmallint", model.getTempSmallint())
//			    .addValue("TempMoney", model.getTempMoney())
//			    .addValue("TempWebsite", model.getTempWebsite())
//			    .addValue("TempType", model.getTempType())
							// .addValue("TypeGroup", model.getTypeGroup())
//			    .addValue("TempPhone", model.getTempPhone())
//			    .addValue("TempSSN", model.getTempSSN())
//			    .addValue("TempFax", model.getTempFax())
//			    .addValue("TempEmail", model.getTempEmail())
							.addValue("UserID", model.getUserID())
							.addValue("RemoteAddr",model.getRemoteAddr())
				.addValue("retval", 0)

			);
		}catch(Exception e) {
			logger.error(e.getMessage());
		}
		return 0L;
	}

	@Override
	public Long delete(SamplegroupView model) {
		try {
			return sqlRepository.GetLong("TemplateGroup_Delete",
					new MapSqlParameterSource().addValue("GroupCode", model.getGroupCode())
							.addValue("UserID", model.getUserID()).addValue("RemoteAddr", model.getRemoteAddr())
							.addValue("retval", 0));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return 0L;
	}
	
	@Override
	public Long delete(String id, String userId, String remoteAddr) {
		try {
			return sqlRepository.GetLong("TemplateGroup_Delete",new MapSqlParameterSource()
					.addValue("GroupCode", "")
					.addValue("UserID", userId)
					.addValue("RemoteAddr", remoteAddr)
					.addValue("retval", id));
		}catch(Exception e) {
			logger.error(e.getMessage());
		}
		return 0L;
	}

	@Override
	public Long authorize(SamplegroupView model) {
		try {
			return sqlRepository.GetLong("TemplateGroup_Authorize",new MapSqlParameterSource()
					.addValue("GroupCode", model.getGroupCode())
							.addValue("UserID", model.getUserID())
							.addValue("RemoteAddr",model.getRemoteAddr())
							.addValue("retval",model.getID()));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return 0L;
	}
	
	@Override
	public Long uploadFile(Samplegroup model, MultipartFile file) {
		try {
			return sqlRepository.GetLong("TemplateGroup_UploadFile",new MapSqlParameterSource()
					.addValue("GroupCode", model.getGroupCode())
					.addValue("FilePath", file.getOriginalFilename()) // "home.jpg"
					.addValue("FileExt", FilenameUtils.getExtension(file.getOriginalFilename())) // "jpg"
					.addValue("ContentType", file.getContentType()) // "application/octet-stream"
					.addValue("FileImage", file.getBytes())
					.addValue("UserID", model.getUserID())
					.addValue("RemoteAddr", model.getRemoteAddr())
					.addValue("retval", 0));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return 0L;
	}
	
	@Override
	public Long upload(ListType model) {
		try {
			return sqlRepository.GetLong("TemplateGroup_Upload",new MapSqlParameterSource()
					.addValue("ListCode", model.getListViewCode())
					.addValue("LanType", "001")
					.addValue("UserId", model.getUserID())
					.addValue("RemoteAddr", model.getRemoteAddr())
					.addValue("retval", 0));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return 0L;
	}

	@Override
	public Long importData(ListType model) {
		try {
			return sqlRepository.GetLong("TemplateGroup_Import",
					new MapSqlParameterSource().addValue("listcode", model.getListViewCode()).addValue("lantype", "001")
							.addValue("criteria", model.getCriteria()).addValue("UserId", model.getUserID())
							.addValue("RemoteAddr", model.getRemoteAddr()).addValue("retval", 0));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return 0L;
	}
}
