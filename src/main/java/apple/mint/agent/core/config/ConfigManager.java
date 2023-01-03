package apple.mint.agent.core.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

import pep.per.mint.common.util.Util;

/**
 * <pre>
 * 
 * </pre> 
 * @author whoana
 * @date 2022
 */
public class ConfigManager {
    
    public static void main(String[] args) {
        try{
            System.setProperty("apple.mint.home", "/Users/whoana/DEV/workspace-vs/a9/home");
            ConfigManager configManager = new ConfigManager();
            configManager.prepare();
            Config config = configManager.getConfig();
            System.out.println(Util.toJSONPrettyString(config));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    String configHome = "./src/main/resources/config";
	String configFile = "config.json";
	Config config;
 
    public void prepare() throws Exception {
		// configHome = System.getProperty("rose.mary.config.home");
		configHome = System.getProperty("apple.mint.home") + File.separator + "config";
		System.out.println("configHome:" + configHome);
		System.getProperties().list(System.out);

		if (configHome == null) {
			throw new Exception("-Dapple.mint.home={설정홈} 값을 읽을 수 없습니다.(errorcd:APPLEMINT-0001)");
		}
		config = (Config) readObjectFromJson(new File(configHome, configFile), Config.class, null);

	}
    
    public Config getConfig(){
        return this.config;
    }

	Object readObjectFromJson(File dest, Class clazz, String ccsid) throws Exception {
		ObjectMapper jsonMapper = new ObjectMapper();
		jsonMapper.enable(JsonParser.Feature.ALLOW_COMMENTS);
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(dest);
			byte b[] = new byte[(int) dest.length()];
			fis.read(b);
			return jsonMapper.readValue(b, clazz);
		} finally {
			try {
				if (fis != null)
					fis.close();
			} catch (IOException e) {
			}
		}
	}

	private void save() throws Exception {
		try {
			ObjectMapper jsonMapper = new ObjectMapper();
			jsonMapper.enable(JsonParser.Feature.ALLOW_COMMENTS);
			jsonMapper.writeValue(new File(configHome, configFile), config);
		} finally {
		}
	}
    
}
