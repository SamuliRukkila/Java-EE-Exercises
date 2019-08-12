/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.docs.guestbook.model.impl;

import com.liferay.docs.guestbook.model.Guestbook;
import com.liferay.docs.guestbook.model.GuestbookModel;
import com.liferay.docs.guestbook.model.GuestbookSoap;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSON;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.impl.BaseModelImpl;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.io.Serializable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The base model implementation for the Guestbook service. Represents a row in the &quot;GB_Guestbook&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface </code>GuestbookModel</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link GuestbookImpl}.
 * </p>
 *
 * @author liferay
 * @see GuestbookImpl
 * @generated
 */
@JSON(strict = true)
@ProviderType
public class GuestbookModelImpl
	extends BaseModelImpl<Guestbook> implements GuestbookModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a guestbook model instance should use the <code>Guestbook</code> interface instead.
	 */
	public static final String TABLE_NAME = "GB_Guestbook";

	public static final Object[][] TABLE_COLUMNS = {
		{"uuid_", Types.VARCHAR}, {"guestbookId", Types.BIGINT},
		{"groupId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP}, {"modifiedDate", Types.TIMESTAMP},
		{"name", Types.VARCHAR}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
		new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("guestbookId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);
	}

	public static final String TABLE_SQL_CREATE =
		"create table GB_Guestbook (uuid_ VARCHAR(75) null,guestbookId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,name VARCHAR(75) null)";

	public static final String TABLE_SQL_DROP = "drop table GB_Guestbook";

	public static final String ORDER_BY_JPQL =
		" ORDER BY guestbook.guestbookId ASC";

	public static final String ORDER_BY_SQL =
		" ORDER BY GB_Guestbook.guestbookId ASC";

	public static final String DATA_SOURCE = "liferayDataSource";

	public static final String SESSION_FACTORY = "liferaySessionFactory";

	public static final String TX_MANAGER = "liferayTransactionManager";

	public static final long COMPANYID_COLUMN_BITMASK = 1L;

	public static final long GROUPID_COLUMN_BITMASK = 2L;

	public static final long UUID_COLUMN_BITMASK = 4L;

	public static final long GUESTBOOKID_COLUMN_BITMASK = 8L;

	public static void setEntityCacheEnabled(boolean entityCacheEnabled) {
		_entityCacheEnabled = entityCacheEnabled;
	}

	public static void setFinderCacheEnabled(boolean finderCacheEnabled) {
		_finderCacheEnabled = finderCacheEnabled;
	}

	/**
	 * Converts the soap model instance into a normal model instance.
	 *
	 * @param soapModel the soap model instance to convert
	 * @return the normal model instance
	 */
	public static Guestbook toModel(GuestbookSoap soapModel) {
		if (soapModel == null) {
			return null;
		}

		Guestbook model = new GuestbookImpl();

		model.setUuid(soapModel.getUuid());
		model.setGuestbookId(soapModel.getGuestbookId());
		model.setGroupId(soapModel.getGroupId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setUserName(soapModel.getUserName());
		model.setCreateDate(soapModel.getCreateDate());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setName(soapModel.getName());

		return model;
	}

	/**
	 * Converts the soap model instances into normal model instances.
	 *
	 * @param soapModels the soap model instances to convert
	 * @return the normal model instances
	 */
	public static List<Guestbook> toModels(GuestbookSoap[] soapModels) {
		if (soapModels == null) {
			return null;
		}

		List<Guestbook> models = new ArrayList<Guestbook>(soapModels.length);

		for (GuestbookSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public GuestbookModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _guestbookId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setGuestbookId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _guestbookId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return Guestbook.class;
	}

	@Override
	public String getModelClassName() {
		return Guestbook.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		Map<String, Function<Guestbook, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		for (Map.Entry<String, Function<Guestbook, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<Guestbook, Object> attributeGetterFunction =
				entry.getValue();

			attributes.put(
				attributeName, attributeGetterFunction.apply((Guestbook)this));
		}

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Map<String, BiConsumer<Guestbook, Object>> attributeSetterBiConsumers =
			getAttributeSetterBiConsumers();

		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String attributeName = entry.getKey();

			BiConsumer<Guestbook, Object> attributeSetterBiConsumer =
				attributeSetterBiConsumers.get(attributeName);

			if (attributeSetterBiConsumer != null) {
				attributeSetterBiConsumer.accept(
					(Guestbook)this, entry.getValue());
			}
		}
	}

	public Map<String, Function<Guestbook, Object>>
		getAttributeGetterFunctions() {

		return _attributeGetterFunctions;
	}

	public Map<String, BiConsumer<Guestbook, Object>>
		getAttributeSetterBiConsumers() {

		return _attributeSetterBiConsumers;
	}

	private static Function<InvocationHandler, Guestbook>
		_getProxyProviderFunction() {

		Class<?> proxyClass = ProxyUtil.getProxyClass(
			Guestbook.class.getClassLoader(), Guestbook.class,
			ModelWrapper.class);

		try {
			Constructor<Guestbook> constructor =
				(Constructor<Guestbook>)proxyClass.getConstructor(
					InvocationHandler.class);

			return invocationHandler -> {
				try {
					return constructor.newInstance(invocationHandler);
				}
				catch (ReflectiveOperationException roe) {
					throw new InternalError(roe);
				}
			};
		}
		catch (NoSuchMethodException nsme) {
			throw new InternalError(nsme);
		}
	}

	private static final Map<String, Function<Guestbook, Object>>
		_attributeGetterFunctions;
	private static final Map<String, BiConsumer<Guestbook, Object>>
		_attributeSetterBiConsumers;

	static {
		Map<String, Function<Guestbook, Object>> attributeGetterFunctions =
			new LinkedHashMap<String, Function<Guestbook, Object>>();
		Map<String, BiConsumer<Guestbook, ?>> attributeSetterBiConsumers =
			new LinkedHashMap<String, BiConsumer<Guestbook, ?>>();

		attributeGetterFunctions.put("uuid", Guestbook::getUuid);
		attributeSetterBiConsumers.put(
			"uuid", (BiConsumer<Guestbook, String>)Guestbook::setUuid);
		attributeGetterFunctions.put("guestbookId", Guestbook::getGuestbookId);
		attributeSetterBiConsumers.put(
			"guestbookId",
			(BiConsumer<Guestbook, Long>)Guestbook::setGuestbookId);
		attributeGetterFunctions.put("groupId", Guestbook::getGroupId);
		attributeSetterBiConsumers.put(
			"groupId", (BiConsumer<Guestbook, Long>)Guestbook::setGroupId);
		attributeGetterFunctions.put("companyId", Guestbook::getCompanyId);
		attributeSetterBiConsumers.put(
			"companyId", (BiConsumer<Guestbook, Long>)Guestbook::setCompanyId);
		attributeGetterFunctions.put("userId", Guestbook::getUserId);
		attributeSetterBiConsumers.put(
			"userId", (BiConsumer<Guestbook, Long>)Guestbook::setUserId);
		attributeGetterFunctions.put("userName", Guestbook::getUserName);
		attributeSetterBiConsumers.put(
			"userName", (BiConsumer<Guestbook, String>)Guestbook::setUserName);
		attributeGetterFunctions.put("createDate", Guestbook::getCreateDate);
		attributeSetterBiConsumers.put(
			"createDate",
			(BiConsumer<Guestbook, Date>)Guestbook::setCreateDate);
		attributeGetterFunctions.put(
			"modifiedDate", Guestbook::getModifiedDate);
		attributeSetterBiConsumers.put(
			"modifiedDate",
			(BiConsumer<Guestbook, Date>)Guestbook::setModifiedDate);
		attributeGetterFunctions.put("name", Guestbook::getName);
		attributeSetterBiConsumers.put(
			"name", (BiConsumer<Guestbook, String>)Guestbook::setName);

		_attributeGetterFunctions = Collections.unmodifiableMap(
			attributeGetterFunctions);
		_attributeSetterBiConsumers = Collections.unmodifiableMap(
			(Map)attributeSetterBiConsumers);
	}

	@JSON
	@Override
	public String getUuid() {
		if (_uuid == null) {
			return "";
		}
		else {
			return _uuid;
		}
	}

	@Override
	public void setUuid(String uuid) {
		_columnBitmask |= UUID_COLUMN_BITMASK;

		if (_originalUuid == null) {
			_originalUuid = _uuid;
		}

		_uuid = uuid;
	}

	public String getOriginalUuid() {
		return GetterUtil.getString(_originalUuid);
	}

	@JSON
	@Override
	public long getGuestbookId() {
		return _guestbookId;
	}

	@Override
	public void setGuestbookId(long guestbookId) {
		_guestbookId = guestbookId;
	}

	@JSON
	@Override
	public long getGroupId() {
		return _groupId;
	}

	@Override
	public void setGroupId(long groupId) {
		_columnBitmask |= GROUPID_COLUMN_BITMASK;

		if (!_setOriginalGroupId) {
			_setOriginalGroupId = true;

			_originalGroupId = _groupId;
		}

		_groupId = groupId;
	}

	public long getOriginalGroupId() {
		return _originalGroupId;
	}

	@JSON
	@Override
	public long getCompanyId() {
		return _companyId;
	}

	@Override
	public void setCompanyId(long companyId) {
		_columnBitmask |= COMPANYID_COLUMN_BITMASK;

		if (!_setOriginalCompanyId) {
			_setOriginalCompanyId = true;

			_originalCompanyId = _companyId;
		}

		_companyId = companyId;
	}

	public long getOriginalCompanyId() {
		return _originalCompanyId;
	}

	@JSON
	@Override
	public long getUserId() {
		return _userId;
	}

	@Override
	public void setUserId(long userId) {
		_userId = userId;
	}

	@Override
	public String getUserUuid() {
		try {
			User user = UserLocalServiceUtil.getUserById(getUserId());

			return user.getUuid();
		}
		catch (PortalException pe) {
			return "";
		}
	}

	@Override
	public void setUserUuid(String userUuid) {
	}

	@JSON
	@Override
	public String getUserName() {
		if (_userName == null) {
			return "";
		}
		else {
			return _userName;
		}
	}

	@Override
	public void setUserName(String userName) {
		_userName = userName;
	}

	@JSON
	@Override
	public Date getCreateDate() {
		return _createDate;
	}

	@Override
	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	@JSON
	@Override
	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public boolean hasSetModifiedDate() {
		return _setModifiedDate;
	}

	@Override
	public void setModifiedDate(Date modifiedDate) {
		_setModifiedDate = true;

		_modifiedDate = modifiedDate;
	}

	@JSON
	@Override
	public String getName() {
		if (_name == null) {
			return "";
		}
		else {
			return _name;
		}
	}

	@Override
	public void setName(String name) {
		_name = name;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return new StagedModelType(
			PortalUtil.getClassNameId(Guestbook.class.getName()));
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(
			getCompanyId(), Guestbook.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public Guestbook toEscapedModel() {
		if (_escapedModel == null) {
			_escapedModel = _escapedModelProxyProviderFunction.apply(
				new AutoEscapeBeanHandler(this));
		}

		return _escapedModel;
	}

	@Override
	public Object clone() {
		GuestbookImpl guestbookImpl = new GuestbookImpl();

		guestbookImpl.setUuid(getUuid());
		guestbookImpl.setGuestbookId(getGuestbookId());
		guestbookImpl.setGroupId(getGroupId());
		guestbookImpl.setCompanyId(getCompanyId());
		guestbookImpl.setUserId(getUserId());
		guestbookImpl.setUserName(getUserName());
		guestbookImpl.setCreateDate(getCreateDate());
		guestbookImpl.setModifiedDate(getModifiedDate());
		guestbookImpl.setName(getName());

		guestbookImpl.resetOriginalValues();

		return guestbookImpl;
	}

	@Override
	public int compareTo(Guestbook guestbook) {
		long primaryKey = guestbook.getPrimaryKey();

		if (getPrimaryKey() < primaryKey) {
			return -1;
		}
		else if (getPrimaryKey() > primaryKey) {
			return 1;
		}
		else {
			return 0;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof Guestbook)) {
			return false;
		}

		Guestbook guestbook = (Guestbook)obj;

		long primaryKey = guestbook.getPrimaryKey();

		if (getPrimaryKey() == primaryKey) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return (int)getPrimaryKey();
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _entityCacheEnabled;
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _finderCacheEnabled;
	}

	@Override
	public void resetOriginalValues() {
		GuestbookModelImpl guestbookModelImpl = this;

		guestbookModelImpl._originalUuid = guestbookModelImpl._uuid;

		guestbookModelImpl._originalGroupId = guestbookModelImpl._groupId;

		guestbookModelImpl._setOriginalGroupId = false;

		guestbookModelImpl._originalCompanyId = guestbookModelImpl._companyId;

		guestbookModelImpl._setOriginalCompanyId = false;

		guestbookModelImpl._setModifiedDate = false;

		guestbookModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<Guestbook> toCacheModel() {
		GuestbookCacheModel guestbookCacheModel = new GuestbookCacheModel();

		guestbookCacheModel.uuid = getUuid();

		String uuid = guestbookCacheModel.uuid;

		if ((uuid != null) && (uuid.length() == 0)) {
			guestbookCacheModel.uuid = null;
		}

		guestbookCacheModel.guestbookId = getGuestbookId();

		guestbookCacheModel.groupId = getGroupId();

		guestbookCacheModel.companyId = getCompanyId();

		guestbookCacheModel.userId = getUserId();

		guestbookCacheModel.userName = getUserName();

		String userName = guestbookCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			guestbookCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			guestbookCacheModel.createDate = createDate.getTime();
		}
		else {
			guestbookCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			guestbookCacheModel.modifiedDate = modifiedDate.getTime();
		}
		else {
			guestbookCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		guestbookCacheModel.name = getName();

		String name = guestbookCacheModel.name;

		if ((name != null) && (name.length() == 0)) {
			guestbookCacheModel.name = null;
		}

		return guestbookCacheModel;
	}

	@Override
	public String toString() {
		Map<String, Function<Guestbook, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			4 * attributeGetterFunctions.size() + 2);

		sb.append("{");

		for (Map.Entry<String, Function<Guestbook, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<Guestbook, Object> attributeGetterFunction =
				entry.getValue();

			sb.append(attributeName);
			sb.append("=");
			sb.append(attributeGetterFunction.apply((Guestbook)this));
			sb.append(", ");
		}

		if (sb.index() > 1) {
			sb.setIndex(sb.index() - 1);
		}

		sb.append("}");

		return sb.toString();
	}

	@Override
	public String toXmlString() {
		Map<String, Function<Guestbook, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			5 * attributeGetterFunctions.size() + 4);

		sb.append("<model><model-name>");
		sb.append(getModelClassName());
		sb.append("</model-name>");

		for (Map.Entry<String, Function<Guestbook, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<Guestbook, Object> attributeGetterFunction =
				entry.getValue();

			sb.append("<column><column-name>");
			sb.append(attributeName);
			sb.append("</column-name><column-value><![CDATA[");
			sb.append(attributeGetterFunction.apply((Guestbook)this));
			sb.append("]]></column-value></column>");
		}

		sb.append("</model>");

		return sb.toString();
	}

	private static final Function<InvocationHandler, Guestbook>
		_escapedModelProxyProviderFunction = _getProxyProviderFunction();
	private static boolean _entityCacheEnabled;
	private static boolean _finderCacheEnabled;

	private String _uuid;
	private String _originalUuid;
	private long _guestbookId;
	private long _groupId;
	private long _originalGroupId;
	private boolean _setOriginalGroupId;
	private long _companyId;
	private long _originalCompanyId;
	private boolean _setOriginalCompanyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private boolean _setModifiedDate;
	private String _name;
	private long _columnBitmask;
	private Guestbook _escapedModel;

}