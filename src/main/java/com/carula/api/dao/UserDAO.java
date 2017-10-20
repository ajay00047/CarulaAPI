package com.carula.api.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.GenericStoredProcedure;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.carula.api.beans.CarSetUpRequestBean;
import com.carula.api.beans.MyTripsRequestBean;
import com.carula.api.beans.RequestTripBean;
import com.carula.api.beans.SignUpRequestBean;
import com.carula.api.beans.TripDetailsBean;
import com.carula.api.beans.TripSetUpRequestBean;
import com.carula.api.beans.TripStatusChangeRequestBean;
import com.carula.api.beans.VerifyOTPResponseDataBean;
import com.carula.api.constants.Constants;
import com.carula.api.constants.Query;
import com.carula.api.delegate.DataBean;
import com.carula.api.util.StringUtil;
import com.carula.api.util.Utils;

@Repository
@Transactional
public class UserDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public int getUserIdFromPassKey(String passKey) {
		try {
			int count = jdbcTemplate.queryForObject(Query.GET_USERID_FROM_PASSKEY, new Object[] { passKey },
					Integer.class);
			return count;
		} catch (EmptyResultDataAccessException e) {
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
	
	public boolean updateName(SignUpRequestBean bean) {
		int count = jdbcTemplate.update(Query.UPDATE_NAME, new Object[] { bean.getFirstName(),bean.getLastName(),bean.getMobile() });
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
		try {
			VerifyOTPResponseDataBean bean = jdbcTemplate.queryForObject(Query.GET_USER_DETAILS,
					new Object[] { mobile }, new RowMapper<VerifyOTPResponseDataBean>() {

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
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	// car
	public boolean insertCar(CarSetUpRequestBean bean) {
		if (getCar(bean.getUserId()) == null) {
			int count = jdbcTemplate.update(Query.INSERT_CAR, new Object[] { bean.getUserId(), bean.getCompany(),
					bean.getModel(), bean.getColor(), bean.getNo() });
			return (count > 0 ? true : false);
		} else {
			int count = jdbcTemplate.update(Query.UPDATE_CAR, new Object[] { bean.getCompany(), bean.getModel(),
					bean.getColor(), bean.getNo(), bean.getUserId() });
			return (count > 0 ? true : false);
		}
	}

	public CarSetUpRequestBean getCar(int userId) {
		try {
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
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
		}

		return null;
	}

	// trip
	public boolean insertTrip(TripSetUpRequestBean bean) {
		Object[] args = new Object[] { bean.getUserId(), bean.getTripDetailsBean().getStart(),
				bean.getTripDetailsBean().getStartLat(), bean.getTripDetailsBean().getStartLong(),
				bean.getTripDetailsBean().getDrop(), bean.getTripDetailsBean().getDropLat(),
				bean.getTripDetailsBean().getDropLong(), bean.getTripDetailsBean().getDuration(),
				bean.getTripDetailsBean().getDistance(), bean.getTripDetailsBean().getFare(),
				bean.getTripDetailsBean().getPassengers(),
				Utils.convertJavaDate2Sqldate(
						bean.getTripDetailsBean().getDate() + " " + bean.getTripDetailsBean().getTime()),
				Utils.addDate2Seoncds(bean.getTripDetailsBean().getDate() + " " + bean.getTripDetailsBean().getTime(),
						bean.getTripDetailsBean().getDuration()),
				bean.getTripDetailsBean().getPassengers(), bean.getTripDetailsBean().getOverviewPolylines() };
		if (!checkTripFallsInTime(bean)) {
			int count = jdbcTemplate.update(Query.INSERT_TRIP, args);
			return (count > 0 ? true : false);
		} else {
			return false;
		}
	}

	public boolean checkTripFallsInTime(TripSetUpRequestBean bean) {
		String startDateTime = Utils.convertJavaDate2Sqldate(
				bean.getTripDetailsBean().getDate() + " " + bean.getTripDetailsBean().getTime());
		Object[] args = new Object[] { bean.getUserId(), startDateTime, startDateTime };
		int count = jdbcTemplate.queryForObject(Query.GET_TRIP_COUNT_BETWEEN_TIME, args, Integer.class);
		return (count > 0 ? true : false);
	}

	public List<TripDetailsBean> getTrip(TripSetUpRequestBean bean) {
		List<TripDetailsBean> lstBean = new ArrayList<TripDetailsBean>();
		String seqId = Utils.generateRandomString(7) + Utils.currentEpoch() + Utils.generateOTP(7);
		try {
			String startDateTime = Utils.convertJavaDate2Sqldate(
					bean.getTripDetailsBean().getDate() + " " + bean.getTripDetailsBean().getTime());
			Object[] args = new Object[] { bean.getUserId(), startDateTime, startDateTime,bean.getUserId() };
			
			lstBean = jdbcTemplate.query(Query.GET_TRIP_BETWEEN_TIME, args, new RowMapper<TripDetailsBean>() {

				@Override
				public TripDetailsBean mapRow(ResultSet result, int arg1) throws SQLException {
					TripDetailsBean tripBean = new TripDetailsBean();

					//tripBean.setTripSequenceId(seqId);
					tripBean.setTripId(result.getInt(1));
					tripBean.setTripUserId(result.getInt(2));
					tripBean.setStart(result.getString(3));
					tripBean.setStartLat(result.getDouble(4));
					tripBean.setStartLong(result.getDouble(5));
					tripBean.setDrop(result.getString(6));
					tripBean.setDropLat(result.getDouble(7));
					tripBean.setDropLong(result.getDouble(8));
					tripBean.setDuration(result.getInt(9));
					tripBean.setDistance(result.getInt(10));
					tripBean.setFare(result.getInt(11));
					tripBean.setPassengers(result.getInt(12));
					tripBean.setRemainingPassengers(result.getInt(13));
					tripBean.setStartDateTime(result.getString(14));
					tripBean.setDropDateTime(result.getString(15));
					tripBean.setOverviewPolylines(result.getString(16));
					tripBean.setFullName(result.getString(17));
					tripBean.setDp(result.getString(18));
					tripBean.setMobile(result.getString(19));
					tripBean.setWalkStartLoc(bean.getTripDetailsBean().getStart());
					tripBean.setWalkStartLat(bean.getTripDetailsBean().getStartLat());
					tripBean.setWalkStartLong(bean.getTripDetailsBean().getStartLong());
					tripBean.setWalkDropLoc(bean.getTripDetailsBean().getDrop());
					tripBean.setWalkDropLat(bean.getTripDetailsBean().getDropLat());
					tripBean.setWalkDropLong(bean.getTripDetailsBean().getDropLong());
					tripBean.setWalkStartDateTime(startDateTime);
					//car details
					tripBean.setCompany(result.getString(20));
					tripBean.setModel(result.getString(21));
					tripBean.setColor(result.getString(22));
					tripBean.setNo(result.getString(23));

					return tripBean;
				}

			});
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
		}
		
		for(TripDetailsBean localBean:lstBean){
			if(!StringUtil.isNullOrBlank(localBean.getTripSequenceId())){
				seqId = localBean.getTripSequenceId();
			}
		}
		
		for(TripDetailsBean localBean:lstBean){
			localBean.setTripSequenceId(seqId);
		}
		
		return lstBean;
	}

	public List<TripDetailsBean> getMyTrips(MyTripsRequestBean bean) {
		List<TripDetailsBean> lstBean = new ArrayList<TripDetailsBean>();
		try {
			String query = "";
			String limit = " LIMIT 0,29";
			if (bean.getPage() > 0)
				limit = " LIMIT " + (bean.getPage() * 30) + "," + ((bean.getPage() * 30) + 29);
			Object[] args = new Object[] { bean.getUserId() };

			if (bean.getIam().equals("O")) {
				query = Query.MY_TRIP_BY_ID_OWNER + limit;

				lstBean = jdbcTemplate.query(query, args, new RowMapper<TripDetailsBean>() {

					@Override
					public TripDetailsBean mapRow(ResultSet result, int arg1) throws SQLException {
						TripDetailsBean tripBean = new TripDetailsBean();

						tripBean.setTripId(result.getInt(1));
						tripBean.setStart(result.getString(2));
						tripBean.setStartLat(result.getDouble(3));
						tripBean.setStartLong(result.getDouble(4));
						tripBean.setDrop(result.getString(5));
						tripBean.setDropLat(result.getDouble(6));
						tripBean.setDropLong(result.getDouble(7));
						tripBean.setDuration(result.getInt(8));
						tripBean.setDistance(result.getInt(9));
						tripBean.setFare(result.getInt(10));
						tripBean.setPassengers(result.getInt(11));
						tripBean.setRemainingPassengers(result.getInt(12));
						tripBean.setStartDateTime(result.getString(13));
						tripBean.setDropDateTime(result.getString(14));
						tripBean.setOverviewPolylines(result.getString(15));
						
						if (result.getString(16).equals("PEN"))
							tripBean.setStatus("Scheduled");
						else if (result.getString(16).equals("ONG"))
							tripBean.setStatus("Ongoing");
						else if (result.getString(16).equals("CAN"))
							tripBean.setStatus("Cancelled");
						else if (result.getString(16).equals("COM"))
							tripBean.setStatus("Completed");
						
						tripBean.setRemainingRequests(result.getInt(17));

						return tripBean;
					}

				});
			} else if (bean.getIam().equals("P")) {
				query = Query.MY_TRIP_BY_ID_PASSENGER + limit;

				lstBean = jdbcTemplate.query(query, args, new RowMapper<TripDetailsBean>() {

					@Override
					public TripDetailsBean mapRow(ResultSet result, int arg1) throws SQLException {
						TripDetailsBean tripBean = new TripDetailsBean();

						tripBean.setTripId(result.getInt(1));
						tripBean.setTripUserId(result.getInt(2));
						tripBean.setStart(result.getString(3));
						tripBean.setStartLat(result.getDouble(4));
						tripBean.setStartLong(result.getDouble(5));
						tripBean.setDrop(result.getString(6));
						tripBean.setDropLat(result.getDouble(7));
						tripBean.setDropLong(result.getDouble(8));
						tripBean.setDuration(result.getInt(9));
						tripBean.setDistance(result.getInt(10));
						tripBean.setFare(result.getInt(11));
						tripBean.setPassengers(result.getInt(12));
						tripBean.setRemainingPassengers(result.getInt(13));
						tripBean.setStartDateTime(result.getString(14));
						tripBean.setDropDateTime(result.getString(15));
						tripBean.setOverviewPolylines(result.getString(16));
						tripBean.setFullName(result.getString(17));
						tripBean.setDp(result.getString(18));
						tripBean.setMobile(result.getString(19));

						if (result.getString(20).equals("CAN"))
							tripBean.setStatus("Ride cancelled by Owner");

						tripBean.setTripRequestId(result.getInt(21));

						tripBean.setWalkStartLoc(result.getString(22));
						tripBean.setWalkStartLat(result.getDouble(23));
						tripBean.setWalkStartLong(result.getDouble(24));
						tripBean.setWalkDropLoc(result.getString(25));
						tripBean.setWalkDropLat(result.getDouble(26));
						tripBean.setWalkDropLong(result.getDouble(27));
						tripBean.setWalkPolylinesStart(result.getString(28));
						tripBean.setWalkPolylinesDrop(result.getString(29));

						tripBean.setWalkDistanceStart(result.getInt(30));
						tripBean.setWalkDurationStart(result.getInt(31));

						tripBean.setWalkDistanceDrop(result.getInt(32));
						tripBean.setWalkDurationDrop(result.getInt(33));

						if (!result.getString(20).equals("CAN")) {
							if (result.getString(34).equals("CAN"))
								tripBean.setStatus("Cancelled by You");
							else if (result.getString(34).equals("PEN"))
								tripBean.setStatus("Requested");
							else if (result.getString(34).equals("REJ"))
								tripBean.setStatus("Rejected");
							else if (result.getString(34).equals("ACP"))
								tripBean.setStatus("Accepted");
							else if (result.getString(34).equals("COM"))
								tripBean.setStatus("Completed");
							else if (result.getString(34).equals("ONG"))
								tripBean.setStatus("Ongoing");
						}
						tripBean.setWalkStartDateTime(result.getString(35));
						
						//car details
						tripBean.setCompany(result.getString(36));
						tripBean.setModel(result.getString(37));
						tripBean.setColor(result.getString(38));
						tripBean.setNo(result.getString(39));

						return tripBean;
					}

				});
			}

		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
		}
		return lstBean;
	}

	public List<TripDetailsBean> getTripRequests(MyTripsRequestBean bean) {
		List<TripDetailsBean> lstBean = new ArrayList<TripDetailsBean>();
		try {
			Object[] args = new Object[] { bean.getTripId() };

			lstBean = jdbcTemplate.query(Query.ALL_TRIP_REQUESTS_OWNER, args, new RowMapper<TripDetailsBean>() {

				@Override
				public TripDetailsBean mapRow(ResultSet result, int arg1) throws SQLException {
					TripDetailsBean tripBean = new TripDetailsBean();

					tripBean.setTripId(result.getInt(1));
					tripBean.setTripUserId(result.getInt(2));
					tripBean.setStart(result.getString(3));
					tripBean.setStartLat(result.getDouble(4));
					tripBean.setStartLong(result.getDouble(5));
					tripBean.setDrop(result.getString(6));
					tripBean.setDropLat(result.getDouble(7));
					tripBean.setDropLong(result.getDouble(8));
					tripBean.setDuration(result.getInt(9));
					tripBean.setDistance(result.getInt(10));
					tripBean.setFare(result.getInt(11));
					tripBean.setPassengers(result.getInt(12));
					tripBean.setRemainingPassengers(result.getInt(13));
					tripBean.setStartDateTime(result.getString(14));
					tripBean.setDropDateTime(result.getString(15));
					tripBean.setOverviewPolylines(result.getString(16));
					tripBean.setFullName(result.getString(17));
					tripBean.setDp(result.getString(18));
					tripBean.setMobile(result.getString(19));
					/*
					 * if(result.getString(20).equals("CAN"))
					 * tripBean.setStatus("Ride cancelled by Owner");
					 */

					tripBean.setTripRequestId(result.getInt(21));

					tripBean.setWalkStartLoc(result.getString(22));
					tripBean.setWalkStartLat(result.getDouble(23));
					tripBean.setWalkStartLong(result.getDouble(24));
					tripBean.setWalkDropLoc(result.getString(25));
					tripBean.setWalkDropLat(result.getDouble(26));
					tripBean.setWalkDropLong(result.getDouble(27));
					tripBean.setWalkPolylinesStart(result.getString(28));
					tripBean.setWalkPolylinesDrop(result.getString(29));

					tripBean.setWalkDistanceStart(result.getInt(30));
					tripBean.setWalkDurationStart(result.getInt(31));

					tripBean.setWalkDistanceDrop(result.getInt(32));
					tripBean.setWalkDurationDrop(result.getInt(33));

					tripBean.setStatus(result.getString(34));
					/*
					 * if(result.getString(34).equals("CAN") &&
					 * !result.getString(20).equals("CAN")) tripBean.setStatus(
					 * "Cancelled by You"); else
					 * if(result.getString(34).equals("PEN"))
					 * tripBean.setStatus("Requested"); else
					 * if(result.getString(34).equals("REJ"))
					 * tripBean.setStatus("Rejected"); else
					 * if(result.getString(34).equals("ACP"))
					 * tripBean.setStatus("Accepted");
					 */

					tripBean.setWalkStartDateTime(result.getString(35));

					return tripBean;
				}

			});

		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
		}
		return lstBean;
	}

	// trip
	public String requestTrip(RequestTripBean bean) {
		TripDetailsBean tripDetailsBean = bean.getTripDetailsBean();
		Object[] args = new Object[] { bean.getUserId(), tripDetailsBean.getTripId(),
				tripDetailsBean.getTripSequenceId(), tripDetailsBean.getStartDateTime(),
				tripDetailsBean.getWalkStartLat(), tripDetailsBean.getWalkStartLong(),
				tripDetailsBean.getWalkStartLoc(), tripDetailsBean.getWalkDropLat(), tripDetailsBean.getWalkDropLong(),
				tripDetailsBean.getWalkDropLoc(), tripDetailsBean.getWalkPolylinesStart(),
				tripDetailsBean.getWalkPolylinesDrop(), tripDetailsBean.getWalkDistanceStart(),
				tripDetailsBean.getWalkDurationStart(), tripDetailsBean.getWalkDistanceDrop(),
				tripDetailsBean.getWalkDurationDrop(), Constants.REQUEST_PENDING, Constants.REQUEST_PENDING };

		StoredProcedure procedure = new GenericStoredProcedure();
		procedure.setDataSource(jdbcTemplate.getDataSource());
		procedure.setSql("proc_request_trip");
		procedure.setFunction(false);

		SqlParameter[] parameters = { new SqlParameter("v_req_user_id", Types.INTEGER),
				new SqlParameter("v_trip_id", Types.INTEGER), new SqlParameter("v_trip_sequence_id", Types.VARCHAR),
				new SqlParameter("v_start_date_time", Types.TIMESTAMP), new SqlParameter("v_start_lat", Types.DOUBLE),
				new SqlParameter("v_start_long", Types.DOUBLE), new SqlParameter("v_start_loc", Types.VARCHAR),
				new SqlParameter("v_drop_lat", Types.DOUBLE), new SqlParameter("v_drop_long", Types.DOUBLE),
				new SqlParameter("v_drop_loc", Types.VARCHAR), new SqlParameter("v_walk_polyline_start", Types.VARCHAR),
				new SqlParameter("v_walk_polyline_drop", Types.VARCHAR),
				new SqlParameter("v_walk_dist_start", Types.INTEGER),
				new SqlParameter("v_walk_duration_start", Types.INTEGER),
				new SqlParameter("v_walk_dist_drop", Types.INTEGER),
				new SqlParameter("v_walk_duration_drop", Types.INTEGER), new SqlParameter("v_status", Types.VARCHAR),
				new SqlOutParameter("OUT_STATUS", Types.VARCHAR) };

		procedure.setParameters(parameters);
		procedure.compile();

		Map<String, Object> result = procedure.execute(args);

		return (String) result.get("OUT_STATUS");

	}

	public boolean changeTripStatus(TripStatusChangeRequestBean bean) {
		int count = 0;
		if (bean.getAction().equals("77")) {
			Object[] args = new Object[] { bean.getStatus(), bean.getTripId(), bean.getUserId() };
			count = jdbcTemplate.update(Query.UPDATE_TRIP_STATUS_OWNER, args);
		} else if (bean.getAction().equals("7")) {
			Object[] args = new Object[] { bean.getStatus(), bean.getTripId(), bean.getTripRequestId() };
			count = jdbcTemplate.update(Query.UPDATE_TRIP_STATUS_PASSENGER, args);
		}
		return (count > 0 ? true : false);
	}
}
