--------------------------------------------------
-- Database schema for rhqmt-server
--------------------------------------------------
DROP SCHEMA IF EXISTS rhqmtserver_schema CASCADE;
CREATE SCHEMA rhqmtserver_schema;
SET search_path = rhqmtserver_schema;
--Step should be followed, for the user going to access database for this app
--ALTER USER <username> SET search_path to 'rhqmtserver_schema';
ALTER USER rhqmtadmin SET search_path to 'rhqmtserver_schema';
-- Table to store job details			
CREATE TABLE job_detail(
	id serial not null,
	name character varying(100) not null,
	job_type character varying(100) not null,
	cron_expression character varying(75) null,
	repeat_count integer null,
	repeat_interval integer null,
	from_time timestamp null,
	to_time timestamp null,
	enabled boolean not null default false,
	target_class character varying(300) not null,
	creation_time timestamp not null default statement_timestamp(),
	unique(id),
	primary key(name, job_type)
);
--Table to Store Job status	
CREATE TABLE job_status_message(
	id serial not null,
	job_id integer not null,
	_type character varying(500) null,
	status character varying(500) null,
	message character varying(1000) null,
	creation_time timestamp not null default statement_timestamp(),
	unique(id),
	unique(job_id),
	foreign key (job_id) references job_detail(id) on delete cascade
);
--Table: Real Time Metric Job data
CREATE TABLE metrics_job_data(
	id serial not null,
	job_id integer not null,
	target_server character varying(100) not null,
	tenant_id character varying(3000) not null,
	metric_name_id character varying(3000) not null,
	metric_interval integer null,
	metric_data_count integer not null default 1,
	metric_time_limit integer null,
	metric_data_limit integer null,
	metric_value_lowest double precision not null default 0.0,
	metric_value_highest double precision not null default 100.0,
	validate_result boolean not null default false,
	initial_setup_done boolean not null default false,
	creation_time timestamp not null default statement_timestamp(),
	unique(id),
	foreign key (job_id) references job_detail(id) on delete cascade
);

--Table: Metrics template
CREATE TABLE metrics_template(
	id serial not null,
	name character varying(100) not null,
	tenant_id character varying(300) not null,
	metric_name_id character varying(300) not null,
	metric_interval integer not null default 300,
	metric_count integer null,
	metric_value_lowest double precision not null default 0.0,
	metric_value_highest double precision not null default 100.0,
	metric_limit integer not null default 1000,
	creation_time timestamp not null default statement_timestamp(),
	unique(id),
	unique(name)	
);
