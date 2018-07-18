package net.inconnection.charge.admin.online.vo;

import java.util.ArrayList;
import java.util.List;

public class QueryParamVO {
	private List<String> properties = new ArrayList<String>();
	private List<String> symbols = new ArrayList<String>();
	private List<Object> values = new ArrayList<Object>();
	private String whereSql = "";
	
	public QueryParamVO() {}
	
	public QueryParamVO(String whereSql) {
		this.whereSql = whereSql;
	}

	public QueryParamVO(List<String> properties, List<String> symbols, List<Object> values, String whereSql) {
		this.properties = properties;
		this.symbols = symbols;
		this.values = values;
		this.whereSql = whereSql;
	}

	public QueryParamVO add(String propertie, String symbol, Object value) {
		getProperties().add(propertie);
		getSymbols().add(symbol);
		getValues().add(value);
		return this;
	}

	public List<String> getProperties() {
		return properties;
	}
	public String[] getPropertiesToArray() {
		return properties.toArray(new String[] {});
	}
	public void setProperties(List<String> properties) {
		this.properties = properties;
	}
	public List<String> getSymbols() {
		return symbols;
	}
	public String[] getSymbolsToArray() {
		return symbols.toArray(new String[] {});
	}
	public void setSymbols(List<String> symbols) {
		this.symbols = symbols;
	}
	public List<Object> getValues() {
		return values;
	}
	public Object[] getValuesToArray() {
		return values.toArray();
	}
	public void setValues(List<Object> values) {
		this.values = values;
	}
	public String getWhereSql() {
		return whereSql;
	}
	public void setWhereSql(String whereSql) {
		this.whereSql = whereSql;
	}
}
