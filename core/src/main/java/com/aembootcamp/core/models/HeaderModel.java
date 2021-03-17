package com.aembootcamp.core.models;

import com.day.cq.wcm.api.Page;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class, resourceType = HeaderModel.RESOURCE_TYPE, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class HeaderModel {
	protected static final String RESOURCE_TYPE = "aem/bootcamp/structure/header";
	@ValueMapValue(name = "fileReference")
	private String fileReference;
	
	@ValueMapValue(name = "altText")
	private String altText;

	@ValueMapValue(name = "logoLink")
	private String logoLink;

	@ChildResource(injectionStrategy = InjectionStrategy.DEFAULT)
	private Resource navigations;

	@SlingObject
	private ResourceResolver resourceResolver;
	List<Page> headerNavigationItemList;

	@PostConstruct
	public void init()  {
		headerNavigationItemList = new ArrayList<>();
		 Iterator<Resource> children = null;
		 if (navigations != null) {
			 children = navigations.listChildren();
			 while (children.hasNext()) {
					Resource childResource = children.next();
					
					ValueMap properties = childResource.adaptTo(ValueMap.class);
					if( properties != null) {
						String rootPath = properties.get("rootPath", String.class);
						
						Page navPage = resourceResolver.getResource(rootPath).adaptTo(Page.class);
						if(navPage != null) {
						headerNavigationItemList.add(navPage);
						}
					}
					
					}
			  }
		
		
		
			
		}
	
	public String getFileReference() {
		return fileReference;
	}

	public String getAltText() {
		return altText;
	}

	public String getLogoLink() {
		return logoLink;
	}

	public List<Page> getHeaderNavigationItemsList() {
		return headerNavigationItemList;
	}
}
