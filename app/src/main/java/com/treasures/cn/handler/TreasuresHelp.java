package com.treasures.cn.handler;

import android.text.TextUtils;

import com.treasures.cn.entity.CategoryType;
import com.treasures.cn.entity.FuzzySearch;
import com.treasures.cn.entity.Treasures;
import com.treasures.cn.o2o.ClientApp;
import com.treasures.cn.utils.BusiConst;
import com.treasures.cn.utils.BusiException;
import com.treasures.cn.utils.Constant;
import com.treasures.cn.utils.FileUtil;
import com.treasures.cn.utils.comm.Asyc;
import com.treasures.cn.utils.comm.Callback;
import com.treasures.cn.utils.comm.ExecuteAndReturn;
import com.treasures.cn.utils.sqlite.DBCategotyManage;
import com.treasures.cn.utils.sqlite.DBCipherManager;
import com.treasures.cn.utils.sqlite.DBInPriceManage;
import com.treasures.cn.utils.sqlite.DBOutPriceManage;

import java.util.ArrayList;
import java.util.List;

public class TreasuresHelp {
    private static final Asyc ASYC = new Asyc(TreasuresHelp.class);

    /**
     * 更新Treasures到数据库
     *
     * @param treasures 藏品
     * @throws BusiException 是否异常
     *                       ********************** 注意：之前如果保存过则更新，没有则是最新数据保存到数据库***************
     */
    public static void saveTreasuresCallback(final Treasures treasures) throws BusiException {
        DBCipherManager.updateTreasuresSave(treasures);
    }

    /**
     * @description 描述一下方法的作用 : 将keywordsArr拼接成String
     * @author: WaveJuJu
     */
    public static String getKeywordsStr(List<String> keywordsArr) {
        StringBuilder keywordsStr = new StringBuilder();
        if (keywordsArr != null && keywordsArr.size() > 0) {
            String douHao = ",";
            for (int i = 0; i < keywordsArr.size(); i++) {
                String keywords = keywordsArr.get(i);
                if (i == 0) {
                    keywordsStr.append(keywords);
                    continue;
                }
                keywordsStr.append(douHao).append(keywords);
            }
        }
        return keywordsStr.toString();
    }

    /**
     * @param totalKeywoder
     * @return 拆分关键词
     */
    public static List<String> getBreakKeywordArr(String totalKeywoder) {
        List<String> keyList = new ArrayList<>();
        String[] keyArr = totalKeywoder.split(",");
        for (String key : keyArr) {
            if (TextUtils.isEmpty(key)) {
                continue;
            }
            if (key.contains("，")) {
                String[] key1Arr = key.split("，");
                for (String chKey : key1Arr) {
                    if (TextUtils.isEmpty(chKey)) {
                        continue;
                    }
                    keyList.add(chKey);
                }
            }else{
                keyList.add(key);
            }
        }
        return keyList;
    }

    /**
     * 获取分页数据
     *
     * @param pageIndex 页数
     * @param callback  返回接口
     */
    public static void getPageTreasuresArr(int pageIndex, Callback<List<Treasures>> callback) {
        ASYC.asyncTask(callback, new ExecuteAndReturn<List<Treasures>>() {
            @Override
            protected List<Treasures> execute() throws BusiException {
                return DBCipherManager.getPageTreasures(pageIndex);
            }
        });
    }

    /**
     * 删除treasures
     *
     * @param treasuresId id
     * @param callback    back
     */
    public static void deleteTreasures(String treasuresId, Callback<Boolean> callback) {
        ASYC.asyncTask(callback, new ExecuteAndReturn<Boolean>() {
            @Override
            protected Boolean execute() throws BusiException {
                return DBCipherManager.treasuresDeleteRecycle(treasuresId, BusiConst.RecycleStatus.DELETE);
            }
        });
    }

    /**
     * 回收Treausre
     *
     * @param treasuresId
     * @param callback
     */
    public static void recycleTreasures(String treasuresId, Callback<Boolean> callback) {
        ASYC.asyncTask(callback, new ExecuteAndReturn<Boolean>() {
            @Override
            protected Boolean execute() throws BusiException {
                return DBCipherManager.treasuresDeleteRecycle(treasuresId, BusiConst.RecycleStatus.RECYCLE);
            }
        });
    }

    /**
     * 收藏\取消收藏
     *
     * @param treasures
     * @param callback
     */
    public static void enshrineTreasires(Treasures treasures, Callback<Treasures> callback) {
        ASYC.asyncTask(callback, new ExecuteAndReturn<Treasures>() {
            @Override
            protected Treasures execute() throws BusiException {
                return DBCipherManager.treasuresEnshrine(treasures.getId(), !treasures.isEnshrine());
            }
        });
    }

    /**
     * 获取所有收藏
     *
     * @param callback
     */
    public static void getAllCollectionTreasures(Callback<List<Treasures>> callback) {
        ASYC.asyncTask(callback, new ExecuteAndReturn<List<Treasures>>() {
            @Override
            protected List<Treasures> execute() throws BusiException {
                return DBCipherManager.getCollectionTreasures();
            }
        });
    }

    public static void getAllTreasures(Callback<List<Treasures>> callback) {
        ASYC.asyncTask(callback, new ExecuteAndReturn<List<Treasures>>() {
            @Override
            protected List<Treasures> execute() throws BusiException {
                return DBCipherManager.getAllTreasures();
            }
        });
    }

    /**
     * 获取所有进价总和
     */
    public static void getTotalInProce(Callback<Double> callback) {
        ASYC.asyncTask(callback, new ExecuteAndReturn<Double>() {
            @Override
            protected Double execute() throws BusiException {
                return DBInPriceManage.getAllInPrice();
            }
        });
    }

    /**
     * 获取所有出价总和ut
     */
    public static void getTotalOutProce(Callback<Double> callback) {
        ASYC.asyncTask(callback, new ExecuteAndReturn<Double>() {
            @Override
            protected Double execute() throws BusiException {
                return DBOutPriceManage.getAllOutPrice();
            }
        });
    }

    /**
     * 获取所有回收
     *
     * @param pageIndex
     * @param callback
     */
    public static void getAllDeleteTreasures(int pageIndex, Callback<List<Treasures>> callback) {
        ASYC.asyncTask(callback, new ExecuteAndReturn<List<Treasures>>() {
            @Override
            protected List<Treasures> execute() throws BusiException {
                return DBCipherManager.getAllDeleteTreasures(pageIndex);
            }
        });
    }

    /**
     * 清空所有回收
     *
     * @param callback
     */
    public static void clearAllDeleteTreasures(Callback<Boolean> callback) {
        ASYC.asyncTask(callback, new ExecuteAndReturn<Boolean>() {
            @Override
            protected Boolean execute() throws BusiException {
                return DBCipherManager.clearAllDeleteTreasure();
            }
        });
    }

    /**
     * 筛选搜索
     *
     * @param fuzzySearch
     * @param pageIndex
     * @param callback
     */
    public static void fuzzySearchTreasures(FuzzySearch fuzzySearch, int pageIndex, Callback<List<Treasures>> callback) {
        ASYC.asyncTask(callback, new ExecuteAndReturn<List<Treasures>>() {
            @Override
            protected List<Treasures> execute() throws BusiException {
                return DBCipherManager.fuzzySearchTreasures(fuzzySearch, pageIndex);
            }
        });
    }

    /**
     * 获取所有不重复的CategoryType
     *
     * @param callback
     */
    public static void getAllTreasureCategoryType(Callback<List<CategoryType>> callback) {
        ASYC.asyncTask(callback, new ExecuteAndReturn<List<CategoryType>>() {
            @Override
            protected List<CategoryType> execute() throws BusiException {
                List<CategoryType> categoryTypes = new ArrayList<>();
                List<Integer> typeIdArr = DBCategotyManage.getAllCategoryTypeIdArr();
                for (int id : typeIdArr) {
                    CategoryType categoryType = CategoryHelp.getCategoryById(id);
                    if (categoryType != null) {
                        categoryTypes.add(categoryType);
                    }
                }
                return categoryTypes;
            }
        });
    }

    /**
     * 获取所有 Keywords
     *
     * @param callback
     */
    public static void getAllTreasuresKeywordsType(Callback<List<String>> callback) {
        ASYC.asyncTask(callback, new ExecuteAndReturn<List<String>>() {
            @Override
            protected List<String> execute() throws BusiException {
                List<String> keywords = new ArrayList<>();
                List<Treasures> treasuresList = DBCipherManager.getAllTreasures();
                if (treasuresList != null && treasuresList.size() > 0) {
                    for (Treasures treasures : treasuresList) {
                        for (String key : treasures.getKeywordsArr())
                            if (!keywords.contains(key)) {
                                keywords.add(key);
                            }
                    }
                }
                return keywords;
            }
        });
    }



    public static List<String> getCopyImgPath(String id,List<String> oldPaths) {
        List<String> newPathArr = new ArrayList<>();
        for (String imgUri : oldPaths){
            String imageName = getCopyImageName(imgUri);
            String newPath = ClientApp.mInstance.mTreasureFile + id + imageName;
            if (FileUtil.fileCopy(imgUri, newPath)){
                newPathArr.add(newPath);
            }
        }
        return newPathArr;
    }
    private static String getCopyImageName(String imgPath){
        for (String name:Constant.ImageId.imageNames){
            if (imgPath.contains(name)){
                return name;
            }
        }
        return "";
    }
}
