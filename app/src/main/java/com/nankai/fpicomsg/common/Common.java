package com.nankai.fpicomsg.common;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;

import com.nankai.fpicomsg.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Nankai on 9/9/2016.
 */
public class Common {

    /**
     * @param activity
     * @param toFragment
     */
    public void addFragment(AppCompatActivity activity, Fragment toFragment, int idLayout) {
        String backStateName = toFragment.getClass().getName();
        String fragmentTag = backStateName;
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        boolean fragmentPopped = fragmentManager.popBackStackImmediate(backStateName, 0);
        if (!fragmentPopped && fragmentManager.findFragmentByTag(fragmentTag) == null) { //fragment not in back stack, create it.
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(idLayout, toFragment, fragmentTag);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.addToBackStack(backStateName);
            transaction.commit();
        }
    }

    /**
     * @param activity
     * @param toFragment
     */
    public void replaceFragment(AppCompatActivity activity, Fragment toFragment, int idLayout) {
        String backStateName = toFragment.getClass().getName();
        String fragmentTag = backStateName;
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        boolean fragmentPopped = fragmentManager.popBackStackImmediate(backStateName, 0);
        if (!fragmentPopped && fragmentManager.findFragmentByTag(fragmentTag) == null) { //fragment not in back stack, create it.
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
            transaction.replace(idLayout, toFragment, fragmentTag);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.addToBackStack(backStateName);
            transaction.commit();
        }
    }

    /**
     * @param fromFragment
     * @param toFragment
     */
    public void addChildFragment(Fragment fromFragment, Fragment toFragment, int idLayout) {
        String backStateName = toFragment.getClass().getName();
        String fragmentTag = backStateName;
        FragmentManager fragmentManager = fromFragment.getChildFragmentManager();
        boolean fragmentPopped = fragmentManager.popBackStackImmediate(backStateName, 0);
        if (!fragmentPopped && fragmentManager.findFragmentByTag(fragmentTag) == null) { //fragment not in back stack, create it.
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(idLayout, toFragment, fragmentTag);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.addToBackStack(backStateName);
            transaction.commit();
        }
    }

    /**
     * @param fromFragment
     * @param toFragment
     */
    public void swapChildFragment(Fragment fromFragment, Fragment toFragment, int idLayout) {
        FragmentManager fragmentManager = fromFragment.getChildFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        transaction.replace(idLayout, toFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public String getMd5(String input) throws NoSuchAlgorithmException {
        Log.d("MD5", "Input: " + input);
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(input.getBytes());
        byte byteData[] = md.digest();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++)
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        Log.d("MD5", "Output: " + sb.toString());
        return sb.toString();
    }


    public int pxToDp(int px,Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int dp = Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return dp;
    }
}
