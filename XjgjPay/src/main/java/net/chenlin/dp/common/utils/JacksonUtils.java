package net.chenlin.dp.common.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

/**
 * Jackson工具类(json/xml)
 *
 * @author Andy Wang
 * @date 2017-12-19
 */
public class JacksonUtils {

    private final static ObjectMapper objectMapper = new ObjectMapper();
    private final static XmlMapper xmlMapper = new XmlMapper();

    private JacksonUtils() {

    }

    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public static XmlMapper getXmlMapper() {
        return xmlMapper;
    }

    /**
     * object转化json
     *
     * @param obj
     * @return
     * @throws Exception
     */
    public static String beanToJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * json转对象
     *
     * @param jsonStr
     * @param clazz
     * @return
     * @throws Exception
     */
    public static <T> T jsonToBean(String jsonStr, Class<T> clazz) {
        try {
            return objectMapper.readValue(jsonStr, clazz);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * json转map
     *
     * @param jsonStr
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static <T> Map<String, Object> jsonToMap(String jsonStr)
            throws Exception {
        return objectMapper.readValue(jsonStr, Map.class);
    }

    /**
     * json转map，指定类型
     *
     * @param jsonStr
     * @param clazz
     * @return
     * @throws Exception
     */
    public static <T> Map<String, T> jsonToMap(String jsonStr, Class<T> clazz)
            throws Exception {
        Map<String, Map<String, Object>> map = objectMapper.readValue(jsonStr,
                new TypeReference<Map<String, T>>() {
                });
        Map<String, T> result = new HashMap<String, T>();
        for (Entry<String, Map<String, Object>> entry : map.entrySet()) {
            result.put(entry.getKey(), mapToBean(entry.getValue(), clazz));
        }
        return result;
    }

    /**
     * json数组转list，指定类型
     *
     * @param jsonArrayStr
     * @param clazz
     * @return
     * @throws Exception
     */
    public static <T> List<T> jsonToList(String jsonArrayStr, Class<T> clazz)
            throws Exception {
        List<Map<String, Object>> list = objectMapper.readValue(jsonArrayStr,
                new TypeReference<List<T>>() {
                });
        List<T> result = new ArrayList<T>();
        for (Map<String, Object> map : list) {
            result.add(mapToBean(map, clazz));
        }
        return result;
    }

    /**
     * map转化对象
     */
    @SuppressWarnings("rawtypes")
    public static <T> T mapToBean(Map map, Class<T> clazz) {
        return objectMapper.convertValue(map, clazz);
    }

    /**
     * xml转成对象
     *
     * @param xmlPath
     * @param cls
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> T xmlToBean(String xmlPath, Class<T> cls) {
        T obj = null;
        try {
            obj = xmlMapper.readValue(new File(xmlPath), cls);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return obj;
    }

    /**
     * xml转成对象
     *
     * @param xmlFile
     * @param cls
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> T xmlToBean(File xmlFile, Class<T> cls) {
        T obj = null;
        try {
            obj = xmlMapper.readValue(xmlFile, cls);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return obj;
    }

    /**
     * xml转成对象
     *
     * @param xmlInputStream
     * @param cls
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> T xmlToBean(InputStream xmlInputStream, Class<T> cls) {
        T obj = null;
        try {
            obj = xmlMapper.readValue(xmlInputStream, cls);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return obj;
    }

    /**
     * 对象转成xml
     *
     * @param bean
     * @param <T>
     * @return
     * @throws JsonProcessingException
     */
    public static <T> String beanToXml(T bean) {
        String string = null;
        try {
            string = xmlMapper.writeValueAsString(bean);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return string;
    }

}
