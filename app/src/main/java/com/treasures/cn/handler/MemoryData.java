package com.treasures.cn.handler;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.treasures.cn.entity.Category;
import com.treasures.cn.entity.CategoryType;
import com.treasures.cn.entity.Treasures;
import com.treasures.cn.entity.UserInfo;
import com.treasures.cn.utils.BusiException;

import java.util.ArrayList;
import java.util.List;

public class MemoryData {
    public final static Gson MY_GSON = new Gson();
    public static Gson MY_EXCLUDE_GSON = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    public static UserInfo USER_INFO = new UserInfo();
    public static List<Category> categories = new ArrayList<>();
    private final static String categoryJson = "[{\"id\":\"1\",\"name\":\"金属器\",\"typeArr\":[{\"id\":\"101\",\"name\":\"金器\"},{\"id\":\"102\",\"name\":\"银器\"},{\"id\":\"103\",\"name\":\"铜器\"},{\"id\":\"104\",\"name\":\"铁器\"},{\"id\":\"105\",\"name\":\"锡器\"},{\"id\":\"106\",\"name\":\"其它金属器\"}]},{\"id\":\"2\",\"name\":\"玉器\",\"typeArr\":[{\"id\":\"201\",\"name\":\"古玉\"},{\"id\":\"202\",\"name\":\"新玉\"}]},{\"id\":\"3\",\"name\":\"玻璃器\",\"typeArr\":[{\"id\":\"301\",\"name\":\"水晶器\"},{\"id\":\"302\",\"name\":\"玻璃器\"},{\"id\":\"303\",\"name\":\"琉璃器\"}]},{\"id\":\"4\",\"name\":\"瓷器\",\"typeArr\":[{\"id\":\"401\",\"name\":\"中国古瓷器\"},{\"id\":\"402\",\"name\":\"中国现代瓷器\"},{\"id\":\"403\",\"name\":\"西洋瓷器\"},{\"id\":\"404\",\"name\":\"其它陶瓷\"}]},{\"id\":\"5\",\"name\":\"纺织品皮具\",\"typeArr\":[{\"id\":\"501\",\"name\":\"衣物\"},{\"id\":\"502\",\"name\":\"手袋包包\"},{\"id\":\"503\",\"name\":\"鞋\"},{\"id\":\"504\",\"name\":\"刺绣、织锦\"},{\"id\":\"505\",\"name\":\"地毯挂毯\"}]},{\"id\":\"6\",\"name\":\"纸质品\",\"typeArr\":[{\"id\":\"601\",\"name\":\"报纸杂志\"},{\"id\":\"602\",\"name\":\"书籍\"},{\"id\":\"603\",\"name\":\"文献、手稿\"},{\"id\":\"604\",\"name\":\"照片\"},{\"id\":\"605\",\"name\":\"信件、明信片\"},{\"id\":\"606\",\"name\":\"海报、广告\"},{\"id\":\"607\",\"name\":\"烟标、酒签、火花等\"}]},{\"id\":\"7\",\"name\":\"雕塑品\",\"typeArr\":[{\"id\":\"701\",\"name\":\"金属雕塑\"},{\"id\":\"702\",\"name\":\"石质雕塑\"},{\"id\":\"703\",\"name\":\"其它雕塑\"}]},{\"id\":\"8\",\"name\":\"首饰\",\"typeArr\":[{\"id\":\"801\",\"name\":\"金银首饰\"},{\"id\":\"802\",\"name\":\"宝石\"},{\"id\":\"803\",\"name\":\"其它首饰\"},{\"id\":\"804\",\"name\":\"配饰\"}]},{\"id\":\"9\",\"name\":\"钱币\",\"typeArr\":[{\"id\":\"901\",\"name\":\"中国古钱币\"},{\"id\":\"902\",\"name\":\"中国金银币\"},{\"id\":\"903\",\"name\":\"中国纸币\"},{\"id\":\"904\",\"name\":\"其它各国钱币\"},{\"id\":\"905\",\"name\":\"其它各国纸币\"},{\"id\":\"906\",\"name\":\"有价证券\"},{\"id\":\"907\",\"name\":\"其它货币\"},{\"id\":\"908\",\"name\":\"纪念章\"}]},{\"id\":\"10\",\"name\":\"邮票\",\"typeArr\":[{\"id\":\"1001\",\"name\":\"中国邮票\"},{\"id\":\"1002\",\"name\":\"世界邮票\"}]},{\"id\":\"11\",\"name\":\"书法绘画\",\"typeArr\":[{\"id\":\"1101\",\"name\":\"书法\"},{\"id\":\"1102\",\"name\":\"中国画\"},{\"id\":\"1103\",\"name\":\"油画\"},{\"id\":\"1104\",\"name\":\"版画\"},{\"id\":\"1105\",\"name\":\"水彩画\"},{\"id\":\"1106\",\"name\":\"素描\"},{\"id\":\"1107\",\"name\":\"其它画\"}]},{\"id\":\"12\",\"name\":\"军事\",\"typeArr\":[{\"id\":\"1201\",\"name\":\"中国兵器\"},{\"id\":\"1202\",\"name\":\"世界兵器\"},{\"id\":\"1203\",\"name\":\"其它军事用品\"}]},{\"id\":\"13\",\"name\":\"文具文玩\",\"typeArr\":[{\"id\":\"1301\",\"name\":\"文房四宝\"},{\"id\":\"1302\",\"name\":\"手串\"},{\"id\":\"1303\",\"name\":\"其它文玩\"},{\"id\":\"1304\",\"name\":\"印章\"},{\"id\":\"1305\",\"name\":\"西洋文具\"}]},{\"id\":\"14\",\"name\":\"玩具\",\"typeArr\":[{\"id\":\"1401\",\"name\":\"手办\"},{\"id\":\"1402\",\"name\":\"老玩具\"},{\"id\":\"1403\",\"name\":\"其它玩具\"}]},{\"id\":\"15\",\"name\":\"其它\",\"typeArr\":[{\"id\":\"1501\",\"name\":\"家具\"},{\"id\":\"1502\",\"name\":\"钟表\"},{\"id\":\"1503\",\"name\":\"乐器\"},{\"id\":\"1504\",\"name\":\"体育相关\"},{\"id\":\"1505\",\"name\":\"动植物标本\"},{\"id\":\"1506\",\"name\":\"矿物陨石标本\"},{\"id\":\"1507\",\"name\":\"奇石盆景\"},{\"id\":\"1508\",\"name\":\"工具\"},{\"id\":\"1509\",\"name\":\"老爷车\"},{\"id\":\"1510\",\"name\":\"现代科技产品\"},{\"id\":\"1511\",\"name\":\"烟酒\"},{\"id\":\"1512\",\"name\":\"旅行纪念品\"}]}]";
    public static List<CategoryType> selectedCategoryTypes = new ArrayList<>();
    public static boolean isLogin = false;

    /**
     * Treasures 转JSON
     *
     * @param treasures 藏品对象
     * @return json String
     * @throws BusiException ..
     */
    public static String serializeTreasures(Treasures treasures) throws BusiException {
        try {
            return MemoryData.MY_GSON.toJson(treasures);
        } catch (Exception e) {
            throw new BusiException("serialize treasures error", e);
        }
    }

    /**
     * json String 转 Treasures
     *
     * @param json json String
     * @return Treasures
     * @throws BusiException .
     */
    public static Treasures deserializeTreasures(String json) throws BusiException {
        try {
            return MemoryData.MY_GSON.fromJson(json, Treasures.class);
        } catch (Exception e) {
            throw new BusiException("deserialize treasures error", e);
        }
    }

    /**
     * UserInfo 转JSON
     *
     * @param userInfo 当前用户
     * @return json String
     * @throws BusiException ..
     */
    public static String serializeUserInfo(UserInfo userInfo) throws BusiException {
        try {
            return MemoryData.MY_GSON.toJson(userInfo);
        } catch (Exception e) {
            throw new BusiException("serialize userInfo error", e);
        }
    }

    /**
     * json String 转 Treasures
     *
     * @param json json String
     * @return Treasures
     * @throws BusiException .
     */
    static UserInfo deserializeUserInfo(String json) throws BusiException {
        try {
            return MemoryData.MY_GSON.fromJson(json, UserInfo.class);
        } catch (Exception e) {
            throw new BusiException("deserialize userInfo error", e);
        }
    }

    /**
     * 序列化解析 Category json to List
     *
     * @return List<Category>
     * @throws BusiException json exception
     */
    static List<Category> deserializaCategoryList() throws BusiException {
        try {
            List<Category> categories = MemoryData.MY_GSON.fromJson(categoryJson, new TypeToken<List<Category>>() {
            }.getType());
            for (Category category : categories) {
                for (CategoryType categoryType : category.getTypeArr()) {
                    categoryType.setCategoryId(category.getId());
                }
            }
            return categories;
        } catch (Exception e) {
            throw new BusiException("deserialize category list error", e);
        }
    }

    /**
     * 将已选的category type id 数组反序列化成json String
     *
     * @return json string
     * @throws BusiException 异常
     */
    static String serializeSelectedCategoryTypesList() throws BusiException {
        try {
            if (MemoryData.selectedCategoryTypes.size() <= 0) {
                throw new BusiException("serialize data is null");
            }
            return MemoryData.MY_GSON.toJson(MemoryData.selectedCategoryTypes);
        } catch (Exception e) {
            throw new BusiException("serialize userInfo error", e);
        }
    }

    /**
     * 序列化 categoty type list 数据
     *
     * @param selectedCategoryTypesJson json string
     * @return List<Integer>
     * @throws BusiException 序列化异常
     */
    static List<CategoryType> deserializaSelectedCategoryTypesList(String selectedCategoryTypesJson) throws BusiException {
        try {
            if (TextUtils.isEmpty(selectedCategoryTypesJson)) {
                throw new BusiException("json string is null");
            }
            return MemoryData.MY_GSON.fromJson(selectedCategoryTypesJson, new TypeToken<List<CategoryType>>() {
            }.getType());
        } catch (Exception e) {
            throw new BusiException("deserialize selected category type list error", e);
        }
    }

    /**
     * user info is null
     *
     * @return
     */
    public static boolean isUserInfoEmpty() {
//        if (TextUtils.isEmpty(USER_INFO.getCreateTime())) {
//            return true;
//        }
//        if (TextUtils.isEmpty(USER_INFO.getNiceName())) {
//            return true;
//        }
        if (TextUtils.isEmpty(USER_INFO.getPassword())) {
            return true;
        }
        return false;
    }


}
