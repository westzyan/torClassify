package com.tor.controller;

import com.tor.domain.ItemStyle;
import com.tor.domain.Relay;
import com.tor.domain.WorldView;
import com.tor.service.RelayService;
import com.tor.utils.RandomColorUtils;
import com.tor.vo.CountryCnt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import sun.util.locale.provider.LocaleProviderAdapter;
import sun.util.locale.provider.ResourceBundleBasedAdapter;
import sun.util.resources.OpenListResourceBundle;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Controller
@Slf4j

public class RelayController {
    @Autowired
    private RelayService initService;


    @RequestMapping(value = "/worldView")
    public String show(ModelMap modelMap) {
        ResourceBundleBasedAdapter resourceBundleBasedAdapter = ((ResourceBundleBasedAdapter) LocaleProviderAdapter.forJRE());
        OpenListResourceBundle resource = resourceBundleBasedAdapter.getLocaleData().getLocaleNames(Locale.UK);

        List<Relay> allList = initService.findAll();
        List<Relay> top = initService.findTop10();
        List<CountryCnt> countryCntList = initService.findByCountry();

        List<WorldView> worldViewArrayList = new ArrayList<>();
        for (CountryCnt c : countryCntList) {
            String country = c.getCountryCode();
            try {
                country = resource.getString(country);
            } catch (Exception e) {
                System.out.println();
            }
            WorldView worldView = new WorldView();
            worldView.setName(country);
            worldView.setValue(c.getCnt());

            String color = new RandomColorUtils().generateColor();
            ItemStyle i = new ItemStyle();
            i.setColor(color);

            worldView.setItemStyle(i);
            worldViewArrayList.add(worldView);
        }

        modelMap.addAttribute("allList", allList);
        modelMap.addAttribute("worldView", worldViewArrayList);
        modelMap.addAttribute("top", top);

        return "worldview";
    }
}
