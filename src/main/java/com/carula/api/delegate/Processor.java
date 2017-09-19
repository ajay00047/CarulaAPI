package com.carula.api.delegate;

import com.carula.api.beans.BaseRequestBean;

public interface Processor {

	public DataBean process(BaseRequestBean bean) throws Exception;
}
