-- --------------
-- Cursor example
-- --------------

-- Create sequences:
drop sequence TEMP_BASE_SEQ;
drop sequence TEMP_DERIVED_SEQ;

create sequence TEMP_BASE_SEQ
  start with 1
	nomaxvalue;

create sequence TEMP_DERIVED_SEQ
	start with 1
	nomaxvalue;


-- Create tables:
drop table TEMP_DERIVED;
drop table TEMP_BASE;

create table TEMP_BASE (
	base_id integer not null,
	name varchar2(32) not null,
	entry_count integer default 0,
	constraint pk_base
		primary key( base_id )
);

create table TEMP_DERIVED (
	derived_id integer not null,
	base_id integer not null,
	name varchar2(32) not null,
	constraint pk_derived
		primary key(derived_id),
	constraint fk_base
		foreign key( base_id )
		references TEMP_BASE( base_id )
		on delete cascade		
);

-- Bind the sequences to tables:
create or replace trigger TEMP_BASE_INS 
	before insert on TEMP_BASE for each row
begin 
	select 
		TEMP_BASE_SEQ.nextval
	into
		:NEW.base_id
	from
		DUAL;
end TEMP_BASE_INS;
/
create or replace trigger TEMP_DERIVED_INS 
	before insert on TEMP_DERIVED for each row
begin 
	select 
		TEMP_DERIVED_SEQ.nextval
	into
		:NEW.derived_id
	from
		DUAL;
end TEMP_DERIVED_INS;
/

-- Prefill the tables:
-- insert into TEMP_BASE( )

