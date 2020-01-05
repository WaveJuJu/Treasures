package com.treasures.cn.handler;

import com.treasures.cn.entity.Category;
import com.treasures.cn.entity.CategoryType;
import com.treasures.cn.entity.Treasures;
import com.treasures.cn.o2o.ClientApp;
import com.treasures.cn.utils.BusiException;
import com.treasures.cn.utils.Constant;
import com.treasures.cn.utils.PrefUtils;
import com.treasures.cn.utils.sqlite.DBCipherManager;

import java.util.List;

public class CategoryHelp {

    /**
     * 添加类型到配置
     *
     * @param categoryType
     */
    public static void addTypeToSetting(CategoryType categoryType) {
        if (CategoryHelp.isSelectedCategoryTypes(categoryType) >= 0) {
            return;
        }
        MemoryData.selectedCategoryTypes.add(0, categoryType);
        saveSelectedCategoryTypes();
    }

    /**
     * 取消类型配置
     */
    public static void cancelTypeToSetting(CategoryType categoryType) {
        int index = CategoryHelp.isSelectedCategoryTypes(categoryType);
        if (index >= 0) {
            MemoryData.selectedCategoryTypes.remove(index);
            saveSelectedCategoryTypes();
        }
    }

    public static int isSelectedCategoryTypes(CategoryType type){
        for (int i = 0; i < MemoryData.selectedCategoryTypes.size(); i++) {
            CategoryType categoryType = MemoryData.selectedCategoryTypes.get(i);
            if (categoryType.getId() == type.getId() && categoryType.getCategoryId() == type.getCategoryId()){
                return i;
            }
        }
        return -1;
    }
    /**
     * 通过typeId查询CategoryType
     *
     * @param typeId CategoryType id
     * @return CategoryType
     */
    public static CategoryType getCategoryById(int typeId) {
        CategoryType categoryType;
        for (Category category : MemoryData.categories) {
            for (CategoryType type : category.getTypeArr()) {
                if (type.getId() == typeId) {
                    return type;
                }
            }
        }
        return null;
    }

    /**
     * 保存已选分类到本地
     */
    private static void saveSelectedCategoryTypes() {
        try {
            String jsonStr = MemoryData.serializeSelectedCategoryTypesList();
            new PrefUtils(ClientApp.mInstance).putString(Constant.SHARED_PREFERENCES_KEY.SP_CATEGORY_TYPE_INFO_KEY, jsonStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询这个分类有多少个reasures 的 count
     *
     * @return
     */
    public static int getCategotyTypeHaveTreasuresCount(int categoryTypeId) {
        int count = 0;
        try {
            List<Treasures> list = DBCipherManager.getAllTreasures();
            if (list != null && list.size() > 0) {
                for (Treasures treasures : list) {
                    if (treasures.getCategoryTypeId() == categoryTypeId) {
                        count += 1;
                    }
                }
            }
            return count;
        } catch (BusiException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
