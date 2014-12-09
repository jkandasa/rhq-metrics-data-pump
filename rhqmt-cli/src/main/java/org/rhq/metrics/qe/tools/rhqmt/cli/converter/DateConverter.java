package org.rhq.metrics.qe.tools.rhqmt.cli.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beust.jcommander.converters.BaseConverter;

public class DateConverter extends BaseConverter<Date>{

	private final Logger _logger = LoggerFactory.getLogger(getClass());
	
	public static final String DATA_FORMAT = "dd/MM/yyyy-HH:mm:ss";
	
	public DateConverter(String optionName) {
		super(optionName);
	}

	@Override
	public Date convert(String dateString) {
		try {
			return new SimpleDateFormat(DATA_FORMAT).parse(dateString);
		} catch (ParseException ex) {
			_logger.error("Exception on conversion, ", ex);
		}
		return null;
	}

}
