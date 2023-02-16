package com.macro.mall.controller;

import com.macro.mall.common.api.CommonPage;
import com.macro.mall.common.api.CommonResult;
import com.macro.mall.dto.PmsProductAttributeParam;
import com.macro.mall.dto.ProductAttrInfo;
import com.macro.mall.model.PmsProductAttribute;
import com.macro.mall.service.PmsProductAttributeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.junit.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 商品属性管理Controller
 * Created by macro on 2018/4/26.
 */
@Controller
@Api(tags = "PmsProductAttributeController")
@Tag(name = "PmsProductAttributeController", description = "商品属性管理")
@RequestMapping("/productAttribute")
public class PmsProductAttributeController {
    @Autowired
    private PmsProductAttributeService productAttributeService;

    @ApiOperation("根据分类查询属性列表或参数列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "type", value = "0表示属性，1表示参数", required = true, paramType = "query", dataType = "integer")})
    @RequestMapping(value = "/list/{cid}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<PmsProductAttribute>> getList(@PathVariable Long cid,
                                                                 @RequestParam(value = "type") Integer type,
                                                                 @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                                 @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        List<PmsProductAttribute> productAttributeList = productAttributeService.getList(cid, type, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(productAttributeList));
    }

    @ApiOperation("添加商品属性信息")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult create(@RequestBody PmsProductAttributeParam productAttributeParam) {
        int count = productAttributeService.create(productAttributeParam);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("修改商品属性信息")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult update(@PathVariable Long id, @RequestBody PmsProductAttributeParam productAttributeParam) {
        int count = productAttributeService.update(id, productAttributeParam);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }

	public class SalePriceLine{
		Integer size = 10;

		public Integer getSize() {
			return size;
		}

		public void setSize(Integer size) {
			this.size = size;
		}

		public String getOuCodes() {
			return ouCodes;
		}

		public void setOuCodes(String ouCodes) {
			this.ouCodes = ouCodes;
		}

		public String getComponyName() {
			return componyName;
		}

		public void setComponyName(String componyName) {
			this.componyName = componyName;
		}

		String ouCodes;
		String componyName;
		public  Integer size(){
			return this.size;
		}
	}

    @ApiOperation("查询单个商品属性")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<PmsProductAttribute> getItem(@PathVariable Long id) {
		PmsProductAttribute productAttribute = productAttributeService.getItem(id);
        return CommonResult.success(productAttribute);
    }

	/**
	 * 按照公司編碼分組存儲在新的map中，ConcurrentHashMap和HashMap效果一樣
	 */
	@Test
	public  void test(){
		List<SalePriceLine> lineList = new ArrayList<>();
		SalePriceLine sp1 = new SalePriceLine();
		sp1.setOuCodes("111");
		sp1.setComponyName("com1111");
		SalePriceLine sp2 = new SalePriceLine();
		sp2.setOuCodes("222");
		sp2.setComponyName("com222");
		SalePriceLine sp3 = new SalePriceLine();
		sp3.setOuCodes("333");
		sp3.setComponyName("com333");
		SalePriceLine sp4 = new SalePriceLine();
		sp4.setOuCodes("111");
		sp4.setComponyName("com444");

		lineList.add(sp1);
		lineList.add(sp2);
		lineList.add(sp3);
		lineList.add(sp4);
		Map<String, Map<Integer, SalePriceLine>> ouLineMap = new ConcurrentHashMap<>(100);
		for (int i = 0; i < lineList.size(); i++) {
			SalePriceLine line = lineList.get(i);
			//如果key不存在則計算得到key的值
			ouLineMap.computeIfAbsent(line.getOuCodes(), key -> new ConcurrentHashMap<>(16)).put(i,line);
		}
		System.out.println(ouLineMap);
	}

    @ApiOperation("批量删除商品属性")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult delete(@RequestParam("ids") List<Long> ids) {
        int count = productAttributeService.delete(ids);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("根据商品分类的id获取商品属性及属性分类")
    @RequestMapping(value = "/attrInfo/{productCategoryId}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<ProductAttrInfo>> getAttrInfo(@PathVariable Long productCategoryId) {
        List<ProductAttrInfo> productAttrInfoList = productAttributeService.getProductAttrInfo(productCategoryId);
        return CommonResult.success(productAttrInfoList);
    }
}
