package com.coolweather.app.model;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

import com.coolweather.app.db.CoolWeatherOpenHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CoolWeatherDB {
	private static final String DB_NAME = "cool_weather";
	private static final int VERSION = 1;
	private static CoolWeatherDB coolWeatherDB;
	private SQLiteDatabase db;
	private  CoolWeatherDB(Context context){
		CoolWeatherOpenHelper dbHelper = new CoolWeatherOpenHelper(context, DB_NAME, null, VERSION);
		dbHelper.getWritableDatabase();
	}
	public synchronized static CoolWeatherDB getInstance(Context context){
		if(coolWeatherDB == null){
			coolWeatherDB = new CoolWeatherDB(context);
		}
		return coolWeatherDB;
	}
	
	public void saveProvice(Provice provice){
		if(provice != null){
			ContentValues values = new ContentValues();
			values.put("provice_name", provice.getProviceName());
			values.put("provice_code", provice.getProviceCode());
			db.insert("Provice", null, values);
		}
	}
	public List<Provice> loadProvices(){
		List<Provice> list = new ArrayList<Provice>();
		Cursor cursor = db.query("Provice", null, null, null, null, null, null);
		if(cursor.moveToFirst()){
			do {
				Provice provice = new Provice();
				provice.setId(cursor.getInt(cursor.getColumnIndex("id")));
				provice.setProviceName(cursor.getString(cursor.getColumnIndex("provice_name")));
				provice.setProviceCode(cursor.getString(cursor.getColumnIndex("provice_code")));
				list.add(provice);
			} while (cursor.moveToNext());
		}
		return list;
	}
	public void saveCity(City city){
		if(city != null){
			ContentValues values = new ContentValues();
			values.put("city_name", city.getCityName());
			values.put("city_code", city.getCityCode());
			values.put("provice_id", city.getProviceId());
			db.insert("City", null, values);
		}
	}
	public List<City> loadCities(int proviceId){
		List<City> list = new ArrayList<City>();
		Cursor cursor = db.query("City", null, "provice_id = ?", new String[]{String.valueOf(proviceId)}, null, null, null);
		if(cursor.moveToFirst()){
			do {
				City city = new City();
				city.setId(cursor.getInt(cursor.getColumnIndex("id")));
				city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
				city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
				city.setProviceId(proviceId);
			} while (cursor.moveToNext());
		}
		return list;
	}
	public void saveCounty(County county){
		if(county != null){
			ContentValues values = new ContentValues();
			values.put("county_name", county.getCountyName());
			values.put("county_code", county.getCountyCode());
			values.put("city_id", county.getCityId());
			db.insert("City", null, values);
		}
	}
	public List<County> loadCounties(int cityId){
		List<County> list = new ArrayList<County>();
		Cursor cursor = db.query("County", null, "city_id = ?", new String[]{String.valueOf(cityId)}, null, null, null);
		if(cursor.moveToFirst()){
			do {
				County county = new County();
				county.setId(cursor.getInt(cursor.getColumnIndex("id")));
				county.setCountyName(cursor.getString(cursor.getColumnIndex("city_name")));
				county.setCountyCode(cursor.getString(cursor.getColumnIndex("city_code")));
				county.setCityId(cityId);
			} while (cursor.moveToNext());
		}
		return list;
	}

}
