package com.carula.api.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.carula.api.beans.CarSetUpRequestBean;
import com.carula.api.beans.GetTripResponseBean;
import com.carula.api.beans.SignUpRequestBean;
import com.carula.api.beans.TripDetailsBean;
import com.carula.api.beans.TripSetUpRequestBean;
import com.carula.api.beans.VerifyOTPResponseDataBean;
import com.carula.api.constants.Query;
import com.carula.api.delegate.DataBean;
import com.carula.api.util.Utils;

@Repository
@Transactional
public class UserDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public int getUserIdFromPassKey(String passKey) {
		try{
		int count = jdbcTemplate.queryForObject(Query.GET_USERID_FROM_PASSKEY, new Object[] { passKey }, Integer.class);
		return count;
		}catch(EmptyResultDataAccessException e){
			e.printStackTrace();
		}
		return 0;
	}

	public boolean mobileAlreadyPresent(String mobile) {
		int count = jdbcTemplate.queryForObject(Query.COUNT_USERS_MOBILE, new Object[] { mobile }, Integer.class);
		return (count > 0 ? true : false);
	}

	public boolean insertUser(SignUpRequestBean bean) {
		int count = jdbcTemplate.update(Query.INSERT_USER,
				new Object[] { bean.getFirstName(), bean.getLastName(), bean.getMobile() });
		return (count > 0 ? true : false);
	}

	public boolean updateOTP(String otp, String mobile) {
		int count = jdbcTemplate.update(Query.UPDATE_USER_OTP, new Object[] { otp, mobile });
		return (count > 0 ? true : false);
	}

	public boolean verifyOTP(String otp, String mobile) {
		int count = jdbcTemplate.queryForObject(Query.VERIFY_OTP, new Object[] { otp, mobile }, Integer.class);
		return (count > 0 ? true : false);
	}

	public boolean updatePassKey(String passKey, String mobile) {
		int count = jdbcTemplate.update(Query.UPDATE_PASS_KEY, new Object[] { passKey, mobile });
		return (count > 0 ? true : false);
	}

	public boolean updateDP(String dpPath, String mobile) {
		int count = jdbcTemplate.update(Query.UPDATE_PASS_KEY, new Object[] { dpPath, mobile });
		return (count > 0 ? true : false);
	}

	public DataBean getUserDetails(String mobile) {
		try{
		VerifyOTPResponseDataBean bean = jdbcTemplate.queryForObject(Query.GET_USER_DETAILS, new Object[] { mobile },
				new RowMapper<VerifyOTPResponseDataBean>() {

					@Override
					public VerifyOTPResponseDataBean mapRow(ResultSet result, int arg1) throws SQLException {
						VerifyOTPResponseDataBean bean = new VerifyOTPResponseDataBean();
						bean.setFirstName(result.getString(2));
						bean.setLastName(result.getString(3));
						bean.setMobile(result.getString(4));
						bean.setDpPath(result.getString(5));
						return bean;
					}

				});
		return bean;
		}catch(EmptyResultDataAccessException e){
			e.printStackTrace();
		}
		return null;
	}

	// car
	public boolean insertCar(CarSetUpRequestBean bean) {
		if (getCar(bean.getUserId()) == null) {
			int count = jdbcTemplate.update(Query.INSERT_CAR,
					new Object[] {  bean.getUserId() ,bean.getCompany(), bean.getModel(), bean.getColor(), bean.getNo() });
			return (count > 0 ? true : false);
		}else{
			int count = jdbcTemplate.update(Query.UPDATE_CAR,
					new Object[] { bean.getCompany(), bean.getModel(), bean.getColor(), bean.getNo(), bean.getUserId() });
			return (count > 0 ? true : false);
		}
	}

	public CarSetUpRequestBean getCar(int userId) {
		try{
		CarSetUpRequestBean bean = jdbcTemplate.queryForObject(Query.GET_CAR, new Object[] { userId },
				new RowMapper<CarSetUpRequestBean>() {

					@Override
					public CarSetUpRequestBean mapRow(ResultSet result, int arg1) throws SQLException {
						CarSetUpRequestBean bean = new CarSetUpRequestBean();
						bean.setCarId(result.getInt(1));
						bean.setUserId(result.getInt(2));
						bean.setCompany(result.getString(3));
						bean.setModel(result.getString(4));
						bean.setColor(result.getString(5));
						bean.setNo(result.getString(6));
						return bean;
					}

				});
		return bean;
		}catch(EmptyResultDataAccessException e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	// trip
	public boolean insertTrip(TripSetUpRequestBean bean) {
		String str = "user_id, start_location, start_lang, start_long, drop_location, drop_lat, drop_long, duration, "+
		"distance, fare, passengers, start_date_time, drop_date_time, polylines";
		Object[] args = new Object[]{bean.getUserId(),bean.getTripDetailsBean().getStart(),bean.getTripDetailsBean().getStartLat()
				,bean.getTripDetailsBean().getStartLong(),bean.getTripDetailsBean().getDrop(),
				bean.getTripDetailsBean().getDropLat(),bean.getTripDetailsBean().getDropLong(),
				bean.getTripDetailsBean().getDuration(),bean.getTripDetailsBean().getDistance(),
				bean.getTripDetailsBean().getFare(),bean.getTripDetailsBean().getPassengers(),
				Utils.convertJavaDate2Sqldate(bean.getTripDetailsBean().getDate()+" "+bean.getTripDetailsBean().getTime()),
				Utils.addDate2Seoncds(bean.getTripDetailsBean().getDate()+" "+bean.getTripDetailsBean().getTime() ,bean.getTripDetailsBean().getDuration()),
				bean.getTripDetailsBean().getFare(),bean.getTripDetailsBean().getPassengers(),
				bean.getTripDetailsBean().getOverviewPolylines()};
		if (!checkTripFallsInTime(bean)) {
			int count = jdbcTemplate.update(Query.INSERT_TRIP,args);
			return (count > 0 ? true : false);
		}else{
			return false;
		}
	}
	
	public boolean checkTripFallsInTime(TripSetUpRequestBean bean) {
		String startDateTime = Utils.convertJavaDate2Sqldate(bean.getTripDetailsBean().getDate()+" "+bean.getTripDetailsBean().getTime());
		Object[] args = new Object[]{bean.getUserId(),startDateTime,startDateTime};
		int count = jdbcTemplate.queryForObject(Query.GET_TRIP_COUNT_BETWEEN_TIME, args, Integer.class);
		return (count > 0 ? true : false);
	}
	
	public List<TripDetailsBean> getTrip(TripSetUpRequestBean bean) {
		List<TripDetailsBean> lstBean=new ArrayList<TripDetailsBean>();
		try{
			String startDateTime = Utils.convertJavaDate2Sqldate(bean.getTripDetailsBean().getDate()+" "+bean.getTripDetailsBean().getTime());
			Object[] args = new Object[]{bean.getUserId(),startDateTime,startDateTime};
			lstBean = jdbcTemplate.query(Query.GET_TRIP_BETWEEN_TIME, args,
				new RowMapper<TripDetailsBean>() {

					@Override
					public TripDetailsBean mapRow(ResultSet result, int arg1) throws SQLException {
						TripDetailsBean bean = new TripDetailsBean();
						
						bean.setTripId(result.getInt(1));
						bean.setTripUserId(result.getInt(2));
						bean.setStart(result.getString(3));
						bean.setStartLat(result.getDouble(4));
						bean.setStartLong(result.getDouble(5));
						bean.setDrop(result.getString(6));
						bean.setDropLat(result.getDouble(7));
						bean.setDropLong(result.getDouble(8));
						bean.setDuration(result.getInt(9));
						bean.setDistance(result.getInt(10));
						bean.setFare(result.getInt(11));
						bean.setPassengers(result.getInt(12));
						bean.setRemainingPassengers(result.getInt(13));
						bean.setStartDateTime(result.getString(14));
						bean.setDropDateTime(result.getString(15));
						bean.setOverviewPolylines(result.getString(16));
						bean.setFullName(result.getString(17));
						bean.setDp(result.getString(18));
						bean.setMobile(result.getString(19));
						
						
						return bean;
					}

				});
		}catch(EmptyResultDataAccessException e){
			e.printStackTrace();
		}
		return lstBean;
	}
}
