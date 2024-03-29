package apple.mint.agent.core.config;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

import pep.per.mint.common.util.Util;
import rose.mary.trace.core.esnecil.S9LManager;

/**
 * <pre>
 * 
 * </pre> 
 * @author whoana
 * @date 2022
 */
public class ConfigManager {
	
	ObjectMapper jsonMapper = new ObjectMapper();
    String configHome = "./src/main/resources/config";
	String configFile = "config.json";
	Config config;
	Settings settings;
 
	public static void main(String[] args) {
		try{
			System.setProperty("apple.mint.home", "/Users/whoana/DEV/workspaces/vsc/a9/home");
            ConfigManager configManager = new ConfigManager();
            configManager.prepare();
            Config config = configManager.getConfig();
            System.out.println(Util.toJSONPrettyString(config));
        }catch(Exception e){
			e.printStackTrace();
        }
    }
	
	Logger logger = LoggerFactory.getLogger(getClass());

	public ConfigManager () throws Exception {
		/* uncomment constructor for activating license  */
		/* 
		try {
            new S9LManager().start();
            logger.info("S9LManager running...");
        } catch (Exception e) {
			logger.error("license problem:", e);
            System.exit(-1);
        }
		*/
		
		jsonMapper.enable(JsonParser.Feature.ALLOW_COMMENTS);
		jsonMapper.enable(JsonParser.Feature.ALLOW_MISSING_VALUES);
		jsonMapper.enable(JsonParser.Feature.ALLOW_TRAILING_COMMA);
	
		// configHome = System.getProperty("rose.mary.config.home");
		configHome = System.getProperty("apple.mint.home") + File.separator + "config";
		System.out.println("configHome:" + configHome);
		System.getProperties().list(System.out);
	
		if (configHome == null) {
			throw new Exception("-Dapple.mint.home={설정홈} 값을 읽을 수 없습니다.(errorcd:APPLEMINT-0001)");
		}
		config = (Config) readObjectFromJson(new File(configHome, configFile), Config.class, null);
	}

    public void prepare() throws Exception {		
		String settingUrl = config.getSettings();
		String address = config.getServerAddress();
		String port = config.getServerPort();
		String serverAddress = "http://" + address + ":" + (Util.isEmpty(port) ? "80" : port);
		URL url = new URL(serverAddress + settingUrl);		
		URLConnection con = url.openConnection();
		ByteArrayOutputStream baos  = new ByteArrayOutputStream();
		InputStream is = con.getInputStream();
		while(true){
			int b = is.read();
			if(b == -1){
				break;
			}
			baos.write(b);
			baos.flush();
		}
		byte[] data = baos.toByteArray();
		settings = (Settings)jsonMapper.readValue(data, Settings.class);			 
	}
    
    public Config getConfig(){
        return this.config;
    }

	public Settings getSettings(){
        return this.settings;
    }

	Object readObjectFromJson(File dest, Class clazz, String ccsid) throws Exception {
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
