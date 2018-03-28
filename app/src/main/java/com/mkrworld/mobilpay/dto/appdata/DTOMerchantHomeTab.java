package com.mkrworld.mobilpay.dto.appdata;

import com.mkrworld.androidlib.utils.Tracer;
import com.mkrworld.mobilpay.BuildConfig;

/**
 * Created by mkr on 14/3/18.
 * Class to hold the data of the Merchant Home Tab
 */
public class DTOMerchantHomeTab {
    private static final String TAG = BuildConfig.BASE_TAG + ".DTOMerchantHomeTab";

    /**
     * TYPE OF THE TAB ON THE HOME SCREEN
     */
    public enum TabType {
        NONE, STATIC_QR_CODE, DYNAMIC_QR_CODE, UPI_COLLECT, AEPS_COLLECT, SEND_BILL, COLLECTION_SUMMARY
    }

    private TabType mTabType;
    private int mIconResId;
    private String mLabel;

    /**
     * Constructor
     *
     * @param tabType
     * @param iconResId
     * @param label
     */
    public DTOMerchantHomeTab(TabType tabType, int iconResId, String label) {
        Tracer.debug(TAG, "DTOMerchantHomeTab: " + tabType + "  " + label);
        mTabType = tabType;
        mIconResId = iconResId;
        mLabel = label;
    }

    /**
     * Method to get the Res Id of the Tab Icon
     *
     * @return
     */
    public int getIconResId() {
        return mIconResId;
    }

    /**
     * Method to get the label of the code
     *
     * @return
     */
    public String getLabel() {
        return mLabel != null ? mLabel : "";
    }

    /**
     * Method to get the RowType
     *
     * @return
     */
    public TabType getTabType() {
        return mTabType != null ? mTabType : TabType.NONE;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof DTOMerchantHomeTab) {
            DTOMerchantHomeTab dtoMerchantHomeTab = (DTOMerchantHomeTab) obj;
            if (dtoMerchantHomeTab.mTabType == mTabType && dtoMerchantHomeTab.mLabel.equalsIgnoreCase(mLabel) && dtoMerchantHomeTab.mIconResId == mIconResId) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return mTabType.hashCode();
    }
}
