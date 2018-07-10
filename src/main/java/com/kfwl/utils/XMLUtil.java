package com.kfwl.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.QName;

/**
 * xml转map类
 * 
 * @author fengfan
 *
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class XMLUtil {
	@SuppressWarnings("unused")
	public static void main(String[] args) throws DocumentException, IOException {
		String textFromFile = "<xml><appid><![CDATA[wx2edff8e7edc68d25]]></appid><bank_type><![CDATA[CFT]]></bank_type><cash_fee><![CDATA[1]]></cash_fee><device_info><![CDATA[WEB]]></device_info><fee_type><![CDATA[CNY]]></fee_type><is_subscribe><![CDATA[Y]]></is_subscribe><mch_id><![CDATA[1314505401]]></mch_id><nonce_str><![CDATA[rt0t798satmrpqp5sgmgp34g7mm9sdgf]]></nonce_str><openid><![CDATA[osU7_v0Sl-gn9sGdftNU8hErZIbY]]></openid><out_trade_no><![CDATA[7998201612011739143675821939]]></out_trade_no><result_code><![CDATA[SUCCESS]]></result_code><return_code><![CDATA[SUCCESS]]></return_code><sign><![CDATA[ECFD281AB222C1A66DB65C2A553082DA]]></sign><time_end><![CDATA[20161201173920]]></time_end><total_fee>1</total_fee><trade_type><![CDATA[JSAPI]]></trade_type><transaction_id><![CDATA[4003832001201612011415141380]]></transaction_id></xml>";
		Map<String, Object> map = xmlToMap(textFromFile, false);
	}

	/**
	 * xml转map 不带属性
	 * 
	 * @param xmlStr
	 * @param needRootKey
	 *            是否需要在返回的map里加根节点键
	 * @return
	 * @throws DocumentException
	 */
	public static Map xmlToMap(String xmlStr, boolean needRootKey) throws DocumentException {
		Document doc = DocumentHelper.parseText(xmlStr);
		Element root = doc.getRootElement();
		Map<String, Object> map = (Map<String, Object>) xmlToMap(root);
		if (root.elements().size() == 0 && root.attributes().size() == 0) {
			return map;
		}
		if (needRootKey) {
			// 在返回的map里加根节点键（如果需要）
			Map<String, Object> rootMap = new HashMap<String, Object>();
			rootMap.put(root.getName(), map);
			return rootMap;
		}
		return map;
	}

	/**
	 * xml转map 带属性
	 * 
	 * @param xmlStr
	 * @param needRootKey
	 *            是否需要在返回的map里加根节点键
	 * @return
	 * @throws DocumentException
	 */
	public static Map xmlToMapWithAttr(String xmlStr, boolean needRootKey) throws DocumentException {
		Document doc = DocumentHelper.parseText(xmlStr);
		Element root = doc.getRootElement();
		Map<String, Object> map = (Map<String, Object>) xmlToMapWithAttr(root);
		if (root.elements().size() == 0 && root.attributes().size() == 0) {
			return map; // 根节点只有一个文本内容
		}
		if (needRootKey) {
			// 在返回的map里加根节点键（如果需要）
			Map<String, Object> rootMap = new HashMap<String, Object>();
			rootMap.put(root.getName(), map);
			return rootMap;
		}
		return map;
	}

	/**
	 * xml转map 不带属性
	 * 
	 * @param element
	 * @return
	 */
	private static Object xmlToMap(Element element) {
		// System.out.println(element.getName());
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		List<Element> elements = element.elements();
		if (elements.size() == 0) {
			map.put(element.getName(), element.getText());
			if (!element.isRootElement()) {
				return element.getText();
			}
		} else if (elements.size() == 1) {
			map.put(elements.get(0).getName(), xmlToMap(elements.get(0)));
		} else if (elements.size() > 1) {
			// 多个子节点的话就得考虑list的情况了，比如多个子节点有节点名称相同的
			// 构造一个map用来去重
			Map<String, Element> tempMap = new LinkedHashMap<String, Element>();
			for (Element ele : elements) {
				tempMap.put(ele.getName(), ele);
			}
			Set<String> keySet = tempMap.keySet();
			for (String string : keySet) {
				Namespace namespace = tempMap.get(string).getNamespace();
				List<Element> elements2 = element.elements(new QName(string, namespace));
				// 如果同名的数目大于1则表示要构建list
				if (elements2.size() > 1) {
					List<Object> list = new ArrayList<Object>();
					for (Element ele : elements2) {
						list.add(xmlToMap(ele));
					}
					map.put(string, list);
				} else {
					// 同名的数量不大于1则直接递归去
					map.put(string, xmlToMap(elements2.get(0)));
				}
			}
		}

		return map;
	}

	/**
	 * xml转map 带属性
	 * 
	 * @param element
	 * @return
	 */
	private static Object xmlToMapWithAttr(Element element) {
		// System.out.println(element.getName());
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		List<Element> elements = element.elements();

		List<Attribute> listAttr = element.attributes(); // 当前节点的所有属性的list
		boolean hasAttributes = false;
		for (Attribute attr : listAttr) {
			hasAttributes = true;
			map.put("@" + attr.getName(), attr.getValue());
		}

		if (elements.size() == 0) {
			// map.put(element.getName(), element.getText());
			if (hasAttributes) {
				map.put("#text", element.getText());
			} else {
				map.put(element.getName(), element.getText());
			}

			if (!element.isRootElement()) {
				// return element.getText();
				if (!hasAttributes) {
					return element.getText();
				}
			}
		} else if (elements.size() == 1) {
			map.put(elements.get(0).getName(), xmlToMapWithAttr(elements.get(0)));
		} else if (elements.size() > 1) {
			// 多个子节点的话就得考虑list的情况了，比如多个子节点有节点名称相同的
			// 构造一个map用来去重
			Map<String, Element> tempMap = new LinkedHashMap<String, Element>();
			for (Element ele : elements) {
				tempMap.put(ele.getName(), ele);
			}
			Set<String> keySet = tempMap.keySet();
			for (String string : keySet) {
				Namespace namespace = tempMap.get(string).getNamespace();
				List<Element> elements2 = element.elements(new QName(string, namespace));
				// 如果同名的数目大于1则表示要构建list
				if (elements2.size() > 1) {
					List<Object> list = new ArrayList<Object>();
					for (Element ele : elements2) {
						list.add(xmlToMapWithAttr(ele));
					}
					map.put(string, list);
				} else {
					// 同名的数量不大于1则直接递归去
					map.put(string, xmlToMapWithAttr(elements2.get(0)));
				}
			}
		}

		return map;
	}

}