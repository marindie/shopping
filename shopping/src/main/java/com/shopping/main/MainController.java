
package com.shopping.main;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.shopping.service.MainService;
import com.shopping.vo.MainVO;

/**
 * Handles requests for the application home page.
 */
@Controller
public class MainController {
	
	private static final Logger logger = LoggerFactory.getLogger(MainController.class);
	@Autowired
	private MainService mainService;
	
	@Autowired
	private ServletContext servletContext;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView main(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		String formattedDate = dateFormat.format(date);
		model.addAttribute("serverTime", formattedDate );
		
		return new ModelAndView("main","command",new MainVO());
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String checkUser(@ModelAttribute MainVO mainVO, Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		logger.info("id ="+mainVO.getId().toString());
		logger.info("pwd ="+mainVO.getPwd());
		
//		model.addAttribute("data", mainService.selectCustomer(mainVO));
		
		return "article";
	}

	@RequestMapping(value = "/article")
	public String upload(@ModelAttribute MainVO mainVO, Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {	
		return "article";
	}
	
	@RequestMapping(value= "selectAllCust", method = {RequestMethod.POST,RequestMethod.GET})
	public String selectAllCust(@ModelAttribute MainVO mainVO, Model model) {
		List<Map<String, Object>> result = mainService.selectAllCust();
//		logger.info(result.toString());
		for(int i = 0 ; i < result.size() ; i++) {
			Map<String, Object> data = result.get(i);
			for(String key : data.keySet()) {
				logger.info(key +" :: "+ data.get(key));
			}
		}
		model.addAttribute("listData", result);
		return "sample/test";
	}
	
	@ResponseBody
	@RequestMapping(value= "ajaxStringSample", method = {RequestMethod.POST,RequestMethod.GET})
	public String ajaxSample(@RequestBody String data) {
		logger.info(data);
		return "ajax response data";
	}
	
	/**
	 * Upload single file using Spring Controller
	 */
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public @ResponseBody
	String uploadFileHandler(@RequestParam("name") String name,
			@RequestParam("file") MultipartFile file) {

		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();

				// Creating the directory to store file
				String rootPath = System.getProperty("catalina.home");
				File dir = new File(rootPath + File.separator + "tmpFiles");
				if (!dir.exists())
					dir.mkdirs();

				// Create the file on server
				File serverFile = new File(dir.getAbsolutePath()
						+ File.separator + name);
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();

				logger.info("Server File Location="
						+ serverFile.getAbsolutePath());

				return "You successfully uploaded file=" + name;
			} catch (Exception e) {
				return "You failed to upload " + name + " => " + e.getMessage();
			}
		} else {
			return "You failed to upload " + name
					+ " because the file was empty.";
		}
	}

	/**
	 * Upload multiple file using Spring Controller
	 */
	@RequestMapping(value = "/uploadMultipleFile", method = RequestMethod.POST)
	public String uploadMultipleFileHandler(@RequestParam("name") String[] names, @RequestParam("file") MultipartFile[] files, ModelMap model, HttpServletRequest request) {
		
		ArrayList<String> imageList = new ArrayList<String>();
		String webappRoot = servletContext.getRealPath("/");
		logger.info(request.getSession().getServletContext().getRealPath("/"));
		logger.info(webappRoot);
		if (files.length != names.length)
			return "Mandatory information missing";

		String message = "";
		for (int i = 0; i < files.length; i++) {
			MultipartFile file = files[i];
			String name = names[i];
			if(!file.isEmpty()) {
				try {	
					if(name.isEmpty()) {
						name = file.getOriginalFilename();
					}
					logger.info(name);
					byte[] bytes = file.getBytes();

					// Creating the directory to store file
					String rootPath = System.getProperty("catalina.home");
					File dir = new File(rootPath + File.separator + "tmpFiles");
					if (!dir.exists())
						dir.mkdirs();
					
					String imagePath = webappRoot;
					File imageDir = new File(imagePath + File.separator + "resources");
					if (!imageDir.exists())
						imageDir.mkdirs();
					
					DateFormat targetFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
					String dt = targetFormat.format(new Date());

					// Create the file on server
					File serverFile = new File(dir.getAbsolutePath() + File.separator + "img_" + dt + "." + file.getOriginalFilename().replaceAll("^.*\\.", ""));
					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
					stream.write(bytes);
					stream.close();
					
					File serverFile2 = new File(imageDir.getAbsolutePath() + File.separator + "img_" + dt + "." + file.getOriginalFilename().replaceAll("^.*\\.", ""));
					BufferedOutputStream stream2 = new BufferedOutputStream(new FileOutputStream(serverFile2));
					stream2.write(bytes);
					stream2.close();
					ResourceLoader resourceLoader = new FileSystemResourceLoader();
					file.transferTo(resourceLoader.getResource("resources/images/test.jpg").getFile());

					logger.info("Server File Location="+ serverFile.getAbsolutePath());
					logger.info("Server File Location="+ serverFile2.getAbsolutePath());
					
					message = message + "You successfully uploaded file=" + name + "<br />";
					
				    // array of supported extensions (use a List if you prefer)
				    final String[] EXTENSIONS = new String[]{
				        "gif", "png", "bmp", "jpg", "jpeg" // and other formats you need
				    };
				    
				    // filter to identify images based on their extensions
				    FilenameFilter IMAGE_FILTER = new FilenameFilter() {
				        @Override
				        public boolean accept(File dir, String name) {
				            for (String ext : EXTENSIONS) {
				                if (name.endsWith("." + ext)) {
				                    return (true);
				                }
				            }
				            return (false);
				        }
				    };
				    
			        if (dir.isDirectory()) { // make sure it's a directory
			            for (final File f : dir.listFiles(IMAGE_FILTER)) {
			                BufferedImage img = null;

			                try {
			                    img = ImageIO.read(f);

			                    // you probably want something more involved here
			                    // to display in your UI
			                    logger.info("image: " + f.getName());
			                    logger.info(" width : " + img.getWidth());
			                    logger.info(" height: " + img.getHeight());
			                    logger.info(" size  : " + f.length());
			                    imageList.add(f.getName());
			                } catch (final IOException e) {
			                    // handle errors here
			                	e.printStackTrace();
			                }
			            }
			        }
			        model.addAttribute("imageList", imageList);
				} catch (Exception e) {
					return "You failed to upload " + name + " => " + e.getMessage();
				}				
			}
		}
		return "load";
	}
	
}
