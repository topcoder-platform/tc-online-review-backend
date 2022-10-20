database tcs_catalog;
-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2000', '1', '17', '132456', '2022-10-08 08:45:22', '132456', '2022-10-08 08:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2000', '3', '1', '132456', '2022-10-08 08:45:22', '132456', '2022-10-08 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2000', '4', '0', '132456', '2022-10-08 08:45:22', '132456', '2022-10-08 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2000', '6', 'Design 2 Round Challenge #2000', '132456', '2022-10-08 08:45:22', '132456', '2022-10-08 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2000', '7', '1.0', '132456', '2022-10-08 08:45:22', '132456', '2022-10-08 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2000', '9', 'On', '132456', '2022-10-08 08:45:22', '132456', '2022-10-08 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2000', '10', 'On', '132456', '2022-10-08 08:45:22', '132456', '2022-10-08 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2000', '11', 'On', '132456', '2022-10-08 08:45:22', '132456', '2022-10-08 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2000', '12', 'Yes', '132456', '2022-10-08 08:45:22', '132456', '2022-10-08 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2000', '13', 'Yes', '132456', '2022-10-08 08:45:22', '132456', '2022-10-08 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2000', '14', 'Open', '132456', '2022-10-08 08:45:22', '132456', '2022-10-08 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2000', '16', '0.0', '132456', '2022-10-08 08:45:22', '132456', '2022-10-08 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2000', '26', 'On', '132456', '2022-10-08 08:45:22', '132456', '2022-10-08 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2000', '29', 'On', '132456', '2022-10-08 08:45:22', '132456', '2022-10-08 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2000', '42', 'true', '132456', '2022-10-08 08:45:22', '132456', '2022-10-08 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2000', '43', 'true', '132456', '2022-10-08 08:45:22', '132456', '2022-10-08 08:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('200013', '2000', '13', '3', '2022-10-08 08:45:22', '2022-10-08 08:45:22', '2022-10-08 08:45:22', NULL, NULL, '0.0', '132456', '2022-10-08 08:45:22', '132456', '2022-10-08 08:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('200014', '2000', '14', '3', '2022-10-08 08:45:22', '2022-10-08 08:45:22', '2022-10-08 09:45:22', NULL, NULL, '3600000.0', '132456', '2022-10-08 08:45:22', '132456', '2022-10-08 08:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20001', '2000', '1', '3', '2022-10-08 09:45:22', '2022-10-08 09:45:22', '2022-10-10 09:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-08 08:45:22', '132456', '2022-10-08 08:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('200015', '2000', '15', '3', '2022-10-08 09:45:22', '2022-10-08 09:45:22', '2022-10-10 09:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-08 08:45:22', '132456', '2022-10-08 08:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('200016', '2000', '16', '3', '2022-10-10 09:45:22', '2022-10-10 09:45:22', '2022-10-10 15:45:22', NULL, NULL, '21600000.0', '132456', '2022-10-08 08:45:22', '132456', '2022-10-08 08:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('200017', '2000', '17', '2', '2022-10-10 15:45:22', '2022-10-10 15:45:22', '2022-10-11 15:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-08 08:45:22', '132456', '2022-10-08 08:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20002', '2000', '2', '2', '2022-10-08 09:45:22', '2022-10-08 09:45:22', '2022-10-12 09:45:22', NULL, NULL, '345600000.0', '132456', '2022-10-08 08:45:22', '132456', '2022-10-08 08:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20003', '2000', '3', '1', '2022-10-12 09:45:22', '2022-10-12 09:45:22', '2022-10-12 21:45:22', NULL, NULL, '43200000.0', '132456', '2022-10-08 08:45:22', '132456', '2022-10-08 08:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20004', '2000', '4', '1', '2022-10-12 21:45:22', '2022-10-12 21:45:22', '2022-10-13 09:45:22', NULL, NULL, '43200000.0', '132456', '2022-10-08 08:45:22', '132456', '2022-10-08 08:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('200011', '2000', '11', '1', '2022-10-13 09:45:22', '2022-10-13 09:45:22', '2022-10-13 21:45:22', NULL, NULL, '43200000.0', '132456', '2022-10-08 08:45:22', '132456', '2022-10-08 08:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20009', '2000', '9', '1', '2022-10-13 21:45:22', '2022-10-13 21:45:22', '2022-10-14 21:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-08 08:45:22', '132456', '2022-10-08 08:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('200010', '2000', '10', '1', '2022-10-14 21:45:22', '2022-10-14 21:45:22', '2022-10-15 03:45:22', NULL, NULL, '21600000.0', '132456', '2022-10-08 08:45:22', '132456', '2022-10-08 08:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('200013', '3', '0', '132456', '2022-10-08 08:45:22', '132456', '2022-10-08 08:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('200013', '5', 'No', '132456', '2022-10-08 08:45:22', '132456', '2022-10-08 08:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('200014', '1', '30000722', '132456', '2022-10-08 08:45:22', '132456', '2022-10-08 08:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('200014', '6', '3', '132456', '2022-10-08 08:45:22', '132456', '2022-10-08 08:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20001', '2', '0', '132456', '2022-10-08 08:45:22', '132456', '2022-10-08 08:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20001', '5', 'No', '132456', '2022-10-08 08:45:22', '132456', '2022-10-08 08:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('200015', '3', '0', '132456', '2022-10-08 08:45:22', '132456', '2022-10-08 08:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('200015', '5', 'No', '132456', '2022-10-08 08:45:22', '132456', '2022-10-08 08:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('200016', '1', '30000416', '132456', '2022-10-08 08:45:22', '132456', '2022-10-08 08:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('200017', '1', '30000417', '132456', '2022-10-08 08:45:22', '132456', '2022-10-08 08:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('200017', '6', '3', '132456', '2022-10-08 08:45:22', '132456', '2022-10-08 08:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20002', '3', '0', '132456', '2022-10-08 08:45:22', '132456', '2022-10-08 08:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20002', '5', 'No', '132456', '2022-10-08 08:45:22', '132456', '2022-10-08 08:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20003', '1', '30000410', '132456', '2022-10-08 08:45:22', '132456', '2022-10-08 08:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20004', '5', 'No', '132456', '2022-10-08 08:45:22', '132456', '2022-10-08 08:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20004', '1', '30000411', '132456', '2022-10-08 08:45:22', '132456', '2022-10-08 08:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20004', '6', '3', '132456', '2022-10-08 08:45:22', '132456', '2022-10-08 08:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('200011', '1', '30000720', '132456', '2022-10-08 08:45:22', '132456', '2022-10-08 08:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20009', '5', 'No', '132456', '2022-10-08 08:45:22', '132456', '2022-10-08 08:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('200010', '5', 'No', '132456', '2022-10-08 08:45:22', '132456', '2022-10-08 08:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('200013', '200014', '1', '1', '0', '132456', '2022-10-08 08:45:22', '132456', '2022-10-08 08:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('200014', '20001', '0', '1', '0', '132456', '2022-10-08 08:45:22', '132456', '2022-10-08 08:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20001', '200015', '0', '1', '0', '132456', '2022-10-08 08:45:22', '132456', '2022-10-08 08:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('200015', '200016', '0', '1', '0', '132456', '2022-10-08 08:45:22', '132456', '2022-10-08 08:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('200016', '200017', '0', '1', '0', '132456', '2022-10-08 08:45:22', '132456', '2022-10-08 08:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('200017', '20002', '0', '1', '0', '132456', '2022-10-08 08:45:22', '132456', '2022-10-08 08:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20002', '20003', '0', '1', '0', '132456', '2022-10-08 08:45:22', '132456', '2022-10-08 08:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20003', '20004', '0', '1', '0', '132456', '2022-10-08 08:45:22', '132456', '2022-10-08 08:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20004', '200011', '0', '1', '0', '132456', '2022-10-08 08:45:22', '132456', '2022-10-08 08:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('200011', '20009', '0', '1', '0', '132456', '2022-10-08 08:45:22', '132456', '2022-10-08 08:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20009', '200010', '0', '1', '0', '132456', '2022-10-08 08:45:22', '132456', '2022-10-08 08:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2000', '13', '2000', NULL, 132456, '132456', '2022-10-08 08:45:22', '132456', '2022-10-08 08:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2000', '1', '132456', '132456', '2022-10-08 08:45:22', '132456', '2022-10-08 08:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2000', '2', 'dok', '132456', '2022-10-08 08:45:22', '132456', '2022-10-08 08:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2001', '1', '38', '132456', '2022-10-05 12:45:22', '132456', '2022-10-05 12:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2001', '3', '1', '132456', '2022-10-05 12:45:22', '132456', '2022-10-05 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2001', '4', '0', '132456', '2022-10-05 12:45:22', '132456', '2022-10-05 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2001', '6', 'First2Finish Challenge #2001', '132456', '2022-10-05 12:45:22', '132456', '2022-10-05 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2001', '7', '1.0', '132456', '2022-10-05 12:45:22', '132456', '2022-10-05 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2001', '9', 'On', '132456', '2022-10-05 12:45:22', '132456', '2022-10-05 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2001', '10', 'On', '132456', '2022-10-05 12:45:22', '132456', '2022-10-05 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2001', '11', 'On', '132456', '2022-10-05 12:45:22', '132456', '2022-10-05 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2001', '12', 'Yes', '132456', '2022-10-05 12:45:22', '132456', '2022-10-05 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2001', '13', 'Yes', '132456', '2022-10-05 12:45:22', '132456', '2022-10-05 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2001', '14', 'Open', '132456', '2022-10-05 12:45:22', '132456', '2022-10-05 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2001', '16', '0.0', '132456', '2022-10-05 12:45:22', '132456', '2022-10-05 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2001', '26', 'On', '132456', '2022-10-05 12:45:22', '132456', '2022-10-05 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2001', '29', 'On', '132456', '2022-10-05 12:45:22', '132456', '2022-10-05 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2001', '42', 'true', '132456', '2022-10-05 12:45:22', '132456', '2022-10-05 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2001', '43', 'true', '132456', '2022-10-05 12:45:22', '132456', '2022-10-05 12:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20011', '2001', '1', '2', '2022-10-05 12:45:22', '2022-10-05 12:45:22', '2022-11-04 12:45:22', NULL, NULL, '2592000000.0', '132456', '2022-10-05 12:45:22', '132456', '2022-10-05 12:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20012', '2001', '2', '2', '2022-10-05 12:45:22', '2022-10-05 12:45:22', '2022-11-04 12:45:22', NULL, NULL, '2592000000.0', '132456', '2022-10-05 12:45:22', '132456', '2022-10-05 12:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('200118', '2001', '18', '3', '2022-10-06 12:45:22', '2022-10-06 12:45:22', '2022-10-07 12:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-05 12:45:22', '132456', '2022-10-05 12:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20011', '2', '0', '132456', '2022-10-05 12:45:22', '132456', '2022-10-05 12:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20011', '5', 'No', '132456', '2022-10-05 12:45:22', '132456', '2022-10-05 12:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20012', '3', '0', '132456', '2022-10-05 12:45:22', '132456', '2022-10-05 12:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20012', '5', 'No', '132456', '2022-10-05 12:45:22', '132456', '2022-10-05 12:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('200118', '1', '30000419', '132456', '2022-10-05 12:45:22', '132456', '2022-10-05 12:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20011', '20012', '1', '1', '0', '132456', '2022-10-05 12:45:22', '132456', '2022-10-05 12:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20012', '200118', '0', '1', '0', '132456', '2022-10-05 12:45:22', '132456', '2022-10-05 12:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2001', '13', '2001', NULL, 132456, '132456', '2022-10-05 12:45:22', '132456', '2022-10-05 12:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2001', '1', '132456', '132456', '2022-10-05 12:45:22', '132456', '2022-10-05 12:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2001', '2', 'dok', '132456', '2022-10-05 12:45:22', '132456', '2022-10-05 12:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2002', '1', '40', '132456', '2022-10-10 11:45:22', '132456', '2022-10-10 11:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2002', '3', '1', '132456', '2022-10-10 11:45:22', '132456', '2022-10-10 11:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2002', '4', '0', '132456', '2022-10-10 11:45:22', '132456', '2022-10-10 11:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2002', '6', 'First2Finish Design Challenge #2002', '132456', '2022-10-10 11:45:22', '132456', '2022-10-10 11:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2002', '7', '1.0', '132456', '2022-10-10 11:45:22', '132456', '2022-10-10 11:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2002', '9', 'On', '132456', '2022-10-10 11:45:22', '132456', '2022-10-10 11:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2002', '10', 'On', '132456', '2022-10-10 11:45:22', '132456', '2022-10-10 11:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2002', '11', 'On', '132456', '2022-10-10 11:45:22', '132456', '2022-10-10 11:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2002', '12', 'Yes', '132456', '2022-10-10 11:45:22', '132456', '2022-10-10 11:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2002', '13', 'Yes', '132456', '2022-10-10 11:45:22', '132456', '2022-10-10 11:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2002', '14', 'Open', '132456', '2022-10-10 11:45:22', '132456', '2022-10-10 11:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2002', '16', '0.0', '132456', '2022-10-10 11:45:22', '132456', '2022-10-10 11:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2002', '26', 'On', '132456', '2022-10-10 11:45:22', '132456', '2022-10-10 11:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2002', '29', 'On', '132456', '2022-10-10 11:45:22', '132456', '2022-10-10 11:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2002', '42', 'true', '132456', '2022-10-10 11:45:22', '132456', '2022-10-10 11:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2002', '43', 'true', '132456', '2022-10-10 11:45:22', '132456', '2022-10-10 11:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20021', '2002', '1', '2', '2022-10-10 11:45:22', '2022-10-10 11:45:22', '2022-11-09 11:45:22', NULL, NULL, '2592000000.0', '132456', '2022-10-10 11:45:22', '132456', '2022-10-10 11:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20022', '2002', '2', '2', '2022-10-10 11:45:22', '2022-10-10 11:45:22', '2022-11-09 11:45:22', NULL, NULL, '2592000000.0', '132456', '2022-10-10 11:45:22', '132456', '2022-10-10 11:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('200218', '2002', '18', '1', '2022-10-11 11:45:22', '2022-10-11 11:45:22', '2022-10-12 11:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-10 11:45:22', '132456', '2022-10-10 11:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20021', '2', '0', '132456', '2022-10-10 11:45:22', '132456', '2022-10-10 11:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20021', '5', 'No', '132456', '2022-10-10 11:45:22', '132456', '2022-10-10 11:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20022', '3', '0', '132456', '2022-10-10 11:45:22', '132456', '2022-10-10 11:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20022', '5', 'No', '132456', '2022-10-10 11:45:22', '132456', '2022-10-10 11:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('200218', '1', '30000419', '132456', '2022-10-10 11:45:22', '132456', '2022-10-10 11:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20021', '20022', '1', '1', '0', '132456', '2022-10-10 11:45:22', '132456', '2022-10-10 11:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20022', '200218', '0', '1', '0', '132456', '2022-10-10 11:45:22', '132456', '2022-10-10 11:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2002', '13', '2002', NULL, 132456, '132456', '2022-10-10 11:45:22', '132456', '2022-10-10 11:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2002', '1', '132456', '132456', '2022-10-10 11:45:22', '132456', '2022-10-10 11:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2002', '2', 'dok', '132456', '2022-10-10 11:45:22', '132456', '2022-10-10 11:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2003', '1', '38', '132456', '2022-10-10 22:45:22', '132456', '2022-10-10 22:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2003', '3', '1', '132456', '2022-10-10 22:45:22', '132456', '2022-10-10 22:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2003', '4', '0', '132456', '2022-10-10 22:45:22', '132456', '2022-10-10 22:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2003', '6', 'First2Finish Challenge #2003', '132456', '2022-10-10 22:45:22', '132456', '2022-10-10 22:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2003', '7', '1.0', '132456', '2022-10-10 22:45:22', '132456', '2022-10-10 22:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2003', '9', 'On', '132456', '2022-10-10 22:45:22', '132456', '2022-10-10 22:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2003', '10', 'On', '132456', '2022-10-10 22:45:22', '132456', '2022-10-10 22:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2003', '11', 'On', '132456', '2022-10-10 22:45:22', '132456', '2022-10-10 22:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2003', '12', 'Yes', '132456', '2022-10-10 22:45:22', '132456', '2022-10-10 22:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2003', '13', 'Yes', '132456', '2022-10-10 22:45:22', '132456', '2022-10-10 22:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2003', '14', 'Open', '132456', '2022-10-10 22:45:22', '132456', '2022-10-10 22:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2003', '16', '0.0', '132456', '2022-10-10 22:45:22', '132456', '2022-10-10 22:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2003', '26', 'On', '132456', '2022-10-10 22:45:22', '132456', '2022-10-10 22:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2003', '29', 'On', '132456', '2022-10-10 22:45:22', '132456', '2022-10-10 22:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2003', '42', 'true', '132456', '2022-10-10 22:45:22', '132456', '2022-10-10 22:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2003', '43', 'true', '132456', '2022-10-10 22:45:22', '132456', '2022-10-10 22:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20031', '2003', '1', '2', '2022-10-10 22:45:22', '2022-10-10 22:45:22', '2022-11-09 22:45:22', NULL, NULL, '2592000000.0', '132456', '2022-10-10 22:45:22', '132456', '2022-10-10 22:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20032', '2003', '2', '2', '2022-10-10 22:45:22', '2022-10-10 22:45:22', '2022-11-09 22:45:22', NULL, NULL, '2592000000.0', '132456', '2022-10-10 22:45:22', '132456', '2022-10-10 22:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('200318', '2003', '18', '1', '2022-10-11 22:45:22', '2022-10-11 22:45:22', '2022-10-12 22:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-10 22:45:22', '132456', '2022-10-10 22:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20031', '2', '0', '132456', '2022-10-10 22:45:22', '132456', '2022-10-10 22:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20031', '5', 'No', '132456', '2022-10-10 22:45:22', '132456', '2022-10-10 22:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20032', '3', '0', '132456', '2022-10-10 22:45:22', '132456', '2022-10-10 22:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20032', '5', 'No', '132456', '2022-10-10 22:45:22', '132456', '2022-10-10 22:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('200318', '1', '30000419', '132456', '2022-10-10 22:45:22', '132456', '2022-10-10 22:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20031', '20032', '1', '1', '0', '132456', '2022-10-10 22:45:22', '132456', '2022-10-10 22:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20032', '200318', '0', '1', '0', '132456', '2022-10-10 22:45:22', '132456', '2022-10-10 22:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2003', '13', '2003', NULL, 132456, '132456', '2022-10-10 22:45:22', '132456', '2022-10-10 22:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2003', '1', '132456', '132456', '2022-10-10 22:45:22', '132456', '2022-10-10 22:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2003', '2', 'dok', '132456', '2022-10-10 22:45:22', '132456', '2022-10-10 22:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2004', '1', '37', '132456', '2022-10-09 05:45:22', '132456', '2022-10-09 05:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2004', '3', '1', '132456', '2022-10-09 05:45:22', '132456', '2022-10-09 05:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2004', '4', '0', '132456', '2022-10-09 05:45:22', '132456', '2022-10-09 05:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2004', '6', 'Data Science Challenge #2004', '132456', '2022-10-09 05:45:22', '132456', '2022-10-09 05:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2004', '7', '1.0', '132456', '2022-10-09 05:45:22', '132456', '2022-10-09 05:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2004', '9', 'On', '132456', '2022-10-09 05:45:22', '132456', '2022-10-09 05:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2004', '10', 'On', '132456', '2022-10-09 05:45:22', '132456', '2022-10-09 05:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2004', '11', 'On', '132456', '2022-10-09 05:45:22', '132456', '2022-10-09 05:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2004', '12', 'Yes', '132456', '2022-10-09 05:45:22', '132456', '2022-10-09 05:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2004', '13', 'Yes', '132456', '2022-10-09 05:45:22', '132456', '2022-10-09 05:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2004', '14', 'Open', '132456', '2022-10-09 05:45:22', '132456', '2022-10-09 05:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2004', '16', '0.0', '132456', '2022-10-09 05:45:22', '132456', '2022-10-09 05:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2004', '26', 'On', '132456', '2022-10-09 05:45:22', '132456', '2022-10-09 05:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2004', '29', 'On', '132456', '2022-10-09 05:45:22', '132456', '2022-10-09 05:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2004', '42', 'true', '132456', '2022-10-09 05:45:22', '132456', '2022-10-09 05:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2004', '43', 'true', '132456', '2022-10-09 05:45:22', '132456', '2022-10-09 05:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20041', '2004', '1', '2', '2022-10-09 05:45:22', '2022-10-09 05:45:22', '2022-10-11 05:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-09 05:45:22', '132456', '2022-10-09 05:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20042', '2004', '2', '2', '2022-10-09 05:45:22', '2022-10-09 05:45:22', '2022-10-11 05:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-09 05:45:22', '132456', '2022-10-09 05:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20044', '2004', '4', '1', '2022-10-11 05:45:22', '2022-10-11 05:45:22', '2022-10-12 05:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-09 05:45:22', '132456', '2022-10-09 05:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20041', '2', '0', '132456', '2022-10-09 05:45:22', '132456', '2022-10-09 05:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20041', '5', 'No', '132456', '2022-10-09 05:45:22', '132456', '2022-10-09 05:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20042', '3', '0', '132456', '2022-10-09 05:45:22', '132456', '2022-10-09 05:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20042', '5', 'No', '132456', '2022-10-09 05:45:22', '132456', '2022-10-09 05:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20044', '5', 'No', '132456', '2022-10-09 05:45:22', '132456', '2022-10-09 05:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20044', '1', '30000413', '132456', '2022-10-09 05:45:22', '132456', '2022-10-09 05:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20041', '20042', '1', '1', '0', '132456', '2022-10-09 05:45:22', '132456', '2022-10-09 05:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20042', '20044', '0', '1', '0', '132456', '2022-10-09 05:45:22', '132456', '2022-10-09 05:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2004', '13', '2004', NULL, 132456, '132456', '2022-10-09 05:45:22', '132456', '2022-10-09 05:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2004', '1', '132456', '132456', '2022-10-09 05:45:22', '132456', '2022-10-09 05:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2004', '2', 'dok', '132456', '2022-10-09 05:45:22', '132456', '2022-10-09 05:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2005', '1', '17', '132456', '2022-10-10 12:45:22', '132456', '2022-10-10 12:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2005', '3', '1', '132456', '2022-10-10 12:45:22', '132456', '2022-10-10 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2005', '4', '0', '132456', '2022-10-10 12:45:22', '132456', '2022-10-10 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2005', '6', 'Design Challenge #2005', '132456', '2022-10-10 12:45:22', '132456', '2022-10-10 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2005', '7', '1.0', '132456', '2022-10-10 12:45:22', '132456', '2022-10-10 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2005', '9', 'On', '132456', '2022-10-10 12:45:22', '132456', '2022-10-10 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2005', '10', 'On', '132456', '2022-10-10 12:45:22', '132456', '2022-10-10 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2005', '11', 'On', '132456', '2022-10-10 12:45:22', '132456', '2022-10-10 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2005', '12', 'Yes', '132456', '2022-10-10 12:45:22', '132456', '2022-10-10 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2005', '13', 'Yes', '132456', '2022-10-10 12:45:22', '132456', '2022-10-10 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2005', '14', 'Open', '132456', '2022-10-10 12:45:22', '132456', '2022-10-10 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2005', '16', '0.0', '132456', '2022-10-10 12:45:22', '132456', '2022-10-10 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2005', '26', 'On', '132456', '2022-10-10 12:45:22', '132456', '2022-10-10 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2005', '29', 'On', '132456', '2022-10-10 12:45:22', '132456', '2022-10-10 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2005', '42', 'true', '132456', '2022-10-10 12:45:22', '132456', '2022-10-10 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2005', '43', 'true', '132456', '2022-10-10 12:45:22', '132456', '2022-10-10 12:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20051', '2005', '1', '2', '2022-10-10 12:45:22', '2022-10-10 12:45:22', '2022-10-12 12:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-10 12:45:22', '132456', '2022-10-10 12:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20052', '2005', '2', '2', '2022-10-10 12:45:22', '2022-10-10 12:45:22', '2022-10-12 12:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-10 12:45:22', '132456', '2022-10-10 12:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20053', '2005', '3', '1', '2022-10-12 12:45:22', '2022-10-12 12:45:22', '2022-10-13 00:45:22', NULL, NULL, '43200000.0', '132456', '2022-10-10 12:45:22', '132456', '2022-10-10 12:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20054', '2005', '4', '1', '2022-10-13 00:45:22', '2022-10-13 00:45:22', '2022-10-14 00:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-10 12:45:22', '132456', '2022-10-10 12:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('200511', '2005', '11', '1', '2022-10-14 00:45:22', '2022-10-14 00:45:22', '2022-10-14 12:45:22', NULL, NULL, '43200000.0', '132456', '2022-10-10 12:45:22', '132456', '2022-10-10 12:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20051', '2', '0', '132456', '2022-10-10 12:45:22', '132456', '2022-10-10 12:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20051', '5', 'No', '132456', '2022-10-10 12:45:22', '132456', '2022-10-10 12:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20052', '3', '0', '132456', '2022-10-10 12:45:22', '132456', '2022-10-10 12:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20052', '5', 'No', '132456', '2022-10-10 12:45:22', '132456', '2022-10-10 12:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20053', '1', '30000410', '132456', '2022-10-10 12:45:22', '132456', '2022-10-10 12:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20054', '5', 'No', '132456', '2022-10-10 12:45:22', '132456', '2022-10-10 12:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20054', '1', '30000411', '132456', '2022-10-10 12:45:22', '132456', '2022-10-10 12:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('200511', '1', '30000720', '132456', '2022-10-10 12:45:22', '132456', '2022-10-10 12:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20051', '20052', '1', '1', '0', '132456', '2022-10-10 12:45:22', '132456', '2022-10-10 12:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20052', '20053', '0', '1', '0', '132456', '2022-10-10 12:45:22', '132456', '2022-10-10 12:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20053', '20054', '0', '1', '0', '132456', '2022-10-10 12:45:22', '132456', '2022-10-10 12:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20054', '200511', '0', '1', '0', '132456', '2022-10-10 12:45:22', '132456', '2022-10-10 12:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2005', '13', '2005', NULL, 132456, '132456', '2022-10-10 12:45:22', '132456', '2022-10-10 12:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2005', '1', '132456', '132456', '2022-10-10 12:45:22', '132456', '2022-10-10 12:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2005', '2', 'dok', '132456', '2022-10-10 12:45:22', '132456', '2022-10-10 12:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2006', '1', '37', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2006', '3', '1', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2006', '4', '0', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2006', '6', 'Data Science Challenge #2006', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2006', '7', '1.0', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2006', '9', 'On', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2006', '10', 'On', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2006', '11', 'On', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2006', '12', 'Yes', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2006', '13', 'Yes', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2006', '14', 'Open', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2006', '16', '0.0', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2006', '26', 'On', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2006', '29', 'On', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2006', '42', 'true', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2006', '43', 'true', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20061', '2006', '1', '2', '2022-10-09 13:45:22', '2022-10-09 13:45:22', '2022-10-11 13:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20062', '2006', '2', '2', '2022-10-09 13:45:22', '2022-10-09 13:45:22', '2022-10-11 13:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20064', '2006', '4', '1', '2022-10-11 13:45:22', '2022-10-11 13:45:22', '2022-10-12 13:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20061', '2', '0', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20061', '5', 'No', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20062', '3', '0', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20062', '5', 'No', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20064', '5', 'No', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20064', '1', '30000413', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20061', '20062', '1', '1', '0', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20062', '20064', '0', '1', '0', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2006', '13', '2006', NULL, 132456, '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2006', '1', '132456', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2006', '2', 'dok', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2007', '1', '37', '132456', '2022-10-07 11:45:22', '132456', '2022-10-07 11:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2007', '3', '1', '132456', '2022-10-07 11:45:22', '132456', '2022-10-07 11:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2007', '4', '0', '132456', '2022-10-07 11:45:22', '132456', '2022-10-07 11:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2007', '6', 'Data Science Challenge #2007', '132456', '2022-10-07 11:45:22', '132456', '2022-10-07 11:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2007', '7', '1.0', '132456', '2022-10-07 11:45:22', '132456', '2022-10-07 11:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2007', '9', 'On', '132456', '2022-10-07 11:45:22', '132456', '2022-10-07 11:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2007', '10', 'On', '132456', '2022-10-07 11:45:22', '132456', '2022-10-07 11:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2007', '11', 'On', '132456', '2022-10-07 11:45:22', '132456', '2022-10-07 11:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2007', '12', 'Yes', '132456', '2022-10-07 11:45:22', '132456', '2022-10-07 11:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2007', '13', 'Yes', '132456', '2022-10-07 11:45:22', '132456', '2022-10-07 11:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2007', '14', 'Open', '132456', '2022-10-07 11:45:22', '132456', '2022-10-07 11:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2007', '16', '0.0', '132456', '2022-10-07 11:45:22', '132456', '2022-10-07 11:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2007', '26', 'On', '132456', '2022-10-07 11:45:22', '132456', '2022-10-07 11:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2007', '29', 'On', '132456', '2022-10-07 11:45:22', '132456', '2022-10-07 11:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2007', '42', 'true', '132456', '2022-10-07 11:45:22', '132456', '2022-10-07 11:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2007', '43', 'true', '132456', '2022-10-07 11:45:22', '132456', '2022-10-07 11:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20071', '2007', '1', '3', '2022-10-07 11:45:22', '2022-10-07 11:45:22', '2022-10-09 11:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-07 11:45:22', '132456', '2022-10-07 11:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20072', '2007', '2', '3', '2022-10-07 11:45:22', '2022-10-07 11:45:22', '2022-10-09 11:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-07 11:45:22', '132456', '2022-10-07 11:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20074', '2007', '4', '3', '2022-10-09 11:45:22', '2022-10-09 11:45:22', '2022-10-10 11:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-07 11:45:22', '132456', '2022-10-07 11:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20071', '2', '0', '132456', '2022-10-07 11:45:22', '132456', '2022-10-07 11:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20071', '5', 'No', '132456', '2022-10-07 11:45:22', '132456', '2022-10-07 11:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20072', '3', '0', '132456', '2022-10-07 11:45:22', '132456', '2022-10-07 11:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20072', '5', 'No', '132456', '2022-10-07 11:45:22', '132456', '2022-10-07 11:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20074', '5', 'No', '132456', '2022-10-07 11:45:22', '132456', '2022-10-07 11:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20074', '1', '30000413', '132456', '2022-10-07 11:45:22', '132456', '2022-10-07 11:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20071', '20072', '1', '1', '0', '132456', '2022-10-07 11:45:22', '132456', '2022-10-07 11:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20072', '20074', '0', '1', '0', '132456', '2022-10-07 11:45:22', '132456', '2022-10-07 11:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2007', '13', '2007', NULL, 132456, '132456', '2022-10-07 11:45:22', '132456', '2022-10-07 11:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2007', '1', '132456', '132456', '2022-10-07 11:45:22', '132456', '2022-10-07 11:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2007', '2', 'dok', '132456', '2022-10-07 11:45:22', '132456', '2022-10-07 11:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2008', '1', '39', '132456', '2022-10-05 08:45:22', '132456', '2022-10-05 08:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2008', '3', '1', '132456', '2022-10-05 08:45:22', '132456', '2022-10-05 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2008', '4', '0', '132456', '2022-10-05 08:45:22', '132456', '2022-10-05 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2008', '6', 'Development Challenge #2008', '132456', '2022-10-05 08:45:22', '132456', '2022-10-05 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2008', '7', '1.0', '132456', '2022-10-05 08:45:22', '132456', '2022-10-05 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2008', '9', 'On', '132456', '2022-10-05 08:45:22', '132456', '2022-10-05 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2008', '10', 'On', '132456', '2022-10-05 08:45:22', '132456', '2022-10-05 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2008', '11', 'On', '132456', '2022-10-05 08:45:22', '132456', '2022-10-05 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2008', '12', 'Yes', '132456', '2022-10-05 08:45:22', '132456', '2022-10-05 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2008', '13', 'Yes', '132456', '2022-10-05 08:45:22', '132456', '2022-10-05 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2008', '14', 'Open', '132456', '2022-10-05 08:45:22', '132456', '2022-10-05 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2008', '16', '0.0', '132456', '2022-10-05 08:45:22', '132456', '2022-10-05 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2008', '26', 'On', '132456', '2022-10-05 08:45:22', '132456', '2022-10-05 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2008', '29', 'On', '132456', '2022-10-05 08:45:22', '132456', '2022-10-05 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2008', '42', 'true', '132456', '2022-10-05 08:45:22', '132456', '2022-10-05 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2008', '43', 'true', '132456', '2022-10-05 08:45:22', '132456', '2022-10-05 08:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20081', '2008', '1', '3', '2022-10-05 08:45:22', '2022-10-05 08:45:22', '2022-10-07 08:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-05 08:45:22', '132456', '2022-10-05 08:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20082', '2008', '2', '3', '2022-10-05 08:45:22', '2022-10-05 08:45:22', '2022-10-07 08:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-05 08:45:22', '132456', '2022-10-05 08:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20084', '2008', '4', '3', '2022-10-07 08:45:22', '2022-10-07 08:45:22', '2022-10-08 08:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-05 08:45:22', '132456', '2022-10-05 08:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20085', '2008', '5', '3', '2022-10-08 08:45:22', '2022-10-08 08:45:22', '2022-10-09 08:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-05 08:45:22', '132456', '2022-10-05 08:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20086', '2008', '6', '3', '2022-10-09 08:45:22', '2022-10-09 08:45:22', '2022-10-09 20:45:22', NULL, NULL, '43200000.0', '132456', '2022-10-05 08:45:22', '132456', '2022-10-05 08:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20081', '2', '0', '132456', '2022-10-05 08:45:22', '132456', '2022-10-05 08:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20081', '5', 'No', '132456', '2022-10-05 08:45:22', '132456', '2022-10-05 08:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20082', '3', '0', '132456', '2022-10-05 08:45:22', '132456', '2022-10-05 08:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20082', '5', 'No', '132456', '2022-10-05 08:45:22', '132456', '2022-10-05 08:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20084', '5', 'No', '132456', '2022-10-05 08:45:22', '132456', '2022-10-05 08:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20084', '1', '30000413', '132456', '2022-10-05 08:45:22', '132456', '2022-10-05 08:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20085', '5', 'No', '132456', '2022-10-05 08:45:22', '132456', '2022-10-05 08:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20085', '4', 'No', '132456', '2022-10-05 08:45:22', '132456', '2022-10-05 08:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20086', '5', 'No', '132456', '2022-10-05 08:45:22', '132456', '2022-10-05 08:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20081', '20082', '1', '1', '0', '132456', '2022-10-05 08:45:22', '132456', '2022-10-05 08:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20082', '20084', '0', '1', '0', '132456', '2022-10-05 08:45:22', '132456', '2022-10-05 08:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20084', '20085', '0', '1', '0', '132456', '2022-10-05 08:45:22', '132456', '2022-10-05 08:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20085', '20086', '0', '1', '0', '132456', '2022-10-05 08:45:22', '132456', '2022-10-05 08:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2008', '13', '2008', NULL, 132456, '132456', '2022-10-05 08:45:22', '132456', '2022-10-05 08:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2008', '1', '132456', '132456', '2022-10-05 08:45:22', '132456', '2022-10-05 08:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2008', '2', 'dok', '132456', '2022-10-05 08:45:22', '132456', '2022-10-05 08:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2009', '1', '17', '132456', '2022-10-07 03:45:22', '132456', '2022-10-07 03:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2009', '3', '1', '132456', '2022-10-07 03:45:22', '132456', '2022-10-07 03:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2009', '4', '0', '132456', '2022-10-07 03:45:22', '132456', '2022-10-07 03:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2009', '6', 'Design 2 Round Challenge #2009', '132456', '2022-10-07 03:45:22', '132456', '2022-10-07 03:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2009', '7', '1.0', '132456', '2022-10-07 03:45:22', '132456', '2022-10-07 03:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2009', '9', 'On', '132456', '2022-10-07 03:45:22', '132456', '2022-10-07 03:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2009', '10', 'On', '132456', '2022-10-07 03:45:22', '132456', '2022-10-07 03:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2009', '11', 'On', '132456', '2022-10-07 03:45:22', '132456', '2022-10-07 03:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2009', '12', 'Yes', '132456', '2022-10-07 03:45:22', '132456', '2022-10-07 03:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2009', '13', 'Yes', '132456', '2022-10-07 03:45:22', '132456', '2022-10-07 03:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2009', '14', 'Open', '132456', '2022-10-07 03:45:22', '132456', '2022-10-07 03:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2009', '16', '0.0', '132456', '2022-10-07 03:45:22', '132456', '2022-10-07 03:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2009', '26', 'On', '132456', '2022-10-07 03:45:22', '132456', '2022-10-07 03:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2009', '29', 'On', '132456', '2022-10-07 03:45:22', '132456', '2022-10-07 03:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2009', '42', 'true', '132456', '2022-10-07 03:45:22', '132456', '2022-10-07 03:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2009', '43', 'true', '132456', '2022-10-07 03:45:22', '132456', '2022-10-07 03:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('200913', '2009', '13', '3', '2022-10-07 03:45:22', '2022-10-07 03:45:22', '2022-10-07 03:45:22', NULL, NULL, '0.0', '132456', '2022-10-07 03:45:22', '132456', '2022-10-07 03:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('200914', '2009', '14', '3', '2022-10-07 03:45:22', '2022-10-07 03:45:22', '2022-10-07 04:45:22', NULL, NULL, '3600000.0', '132456', '2022-10-07 03:45:22', '132456', '2022-10-07 03:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20091', '2009', '1', '3', '2022-10-07 04:45:22', '2022-10-07 04:45:22', '2022-10-09 04:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-07 03:45:22', '132456', '2022-10-07 03:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('200915', '2009', '15', '3', '2022-10-07 04:45:22', '2022-10-07 04:45:22', '2022-10-09 04:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-07 03:45:22', '132456', '2022-10-07 03:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('200916', '2009', '16', '3', '2022-10-09 04:45:22', '2022-10-09 04:45:22', '2022-10-09 10:45:22', NULL, NULL, '21600000.0', '132456', '2022-10-07 03:45:22', '132456', '2022-10-07 03:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('200917', '2009', '17', '3', '2022-10-09 10:45:22', '2022-10-09 10:45:22', '2022-10-10 10:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-07 03:45:22', '132456', '2022-10-07 03:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20092', '2009', '2', '2', '2022-10-07 04:45:22', '2022-10-07 04:45:22', '2022-10-11 04:45:22', NULL, NULL, '345600000.0', '132456', '2022-10-07 03:45:22', '132456', '2022-10-07 03:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20093', '2009', '3', '1', '2022-10-11 04:45:22', '2022-10-11 04:45:22', '2022-10-11 16:45:22', NULL, NULL, '43200000.0', '132456', '2022-10-07 03:45:22', '132456', '2022-10-07 03:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20094', '2009', '4', '1', '2022-10-11 16:45:22', '2022-10-11 16:45:22', '2022-10-12 04:45:22', NULL, NULL, '43200000.0', '132456', '2022-10-07 03:45:22', '132456', '2022-10-07 03:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('200911', '2009', '11', '1', '2022-10-12 04:45:22', '2022-10-12 04:45:22', '2022-10-12 16:45:22', NULL, NULL, '43200000.0', '132456', '2022-10-07 03:45:22', '132456', '2022-10-07 03:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20099', '2009', '9', '1', '2022-10-12 16:45:22', '2022-10-12 16:45:22', '2022-10-13 16:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-07 03:45:22', '132456', '2022-10-07 03:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('200910', '2009', '10', '1', '2022-10-13 16:45:22', '2022-10-13 16:45:22', '2022-10-13 22:45:22', NULL, NULL, '21600000.0', '132456', '2022-10-07 03:45:22', '132456', '2022-10-07 03:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('200913', '3', '0', '132456', '2022-10-07 03:45:22', '132456', '2022-10-07 03:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('200913', '5', 'No', '132456', '2022-10-07 03:45:22', '132456', '2022-10-07 03:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('200914', '1', '30000722', '132456', '2022-10-07 03:45:22', '132456', '2022-10-07 03:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('200914', '6', '3', '132456', '2022-10-07 03:45:22', '132456', '2022-10-07 03:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20091', '2', '0', '132456', '2022-10-07 03:45:22', '132456', '2022-10-07 03:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20091', '5', 'No', '132456', '2022-10-07 03:45:22', '132456', '2022-10-07 03:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('200915', '3', '0', '132456', '2022-10-07 03:45:22', '132456', '2022-10-07 03:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('200915', '5', 'No', '132456', '2022-10-07 03:45:22', '132456', '2022-10-07 03:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('200916', '1', '30000416', '132456', '2022-10-07 03:45:22', '132456', '2022-10-07 03:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('200917', '1', '30000417', '132456', '2022-10-07 03:45:22', '132456', '2022-10-07 03:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('200917', '6', '3', '132456', '2022-10-07 03:45:22', '132456', '2022-10-07 03:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20092', '3', '0', '132456', '2022-10-07 03:45:22', '132456', '2022-10-07 03:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20092', '5', 'No', '132456', '2022-10-07 03:45:22', '132456', '2022-10-07 03:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20093', '1', '30000410', '132456', '2022-10-07 03:45:22', '132456', '2022-10-07 03:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20094', '5', 'No', '132456', '2022-10-07 03:45:22', '132456', '2022-10-07 03:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20094', '1', '30000411', '132456', '2022-10-07 03:45:22', '132456', '2022-10-07 03:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20094', '6', '3', '132456', '2022-10-07 03:45:22', '132456', '2022-10-07 03:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('200911', '1', '30000720', '132456', '2022-10-07 03:45:22', '132456', '2022-10-07 03:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20099', '5', 'No', '132456', '2022-10-07 03:45:22', '132456', '2022-10-07 03:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('200910', '5', 'No', '132456', '2022-10-07 03:45:22', '132456', '2022-10-07 03:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('200913', '200914', '1', '1', '0', '132456', '2022-10-07 03:45:22', '132456', '2022-10-07 03:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('200914', '20091', '0', '1', '0', '132456', '2022-10-07 03:45:22', '132456', '2022-10-07 03:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20091', '200915', '0', '1', '0', '132456', '2022-10-07 03:45:22', '132456', '2022-10-07 03:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('200915', '200916', '0', '1', '0', '132456', '2022-10-07 03:45:22', '132456', '2022-10-07 03:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('200916', '200917', '0', '1', '0', '132456', '2022-10-07 03:45:22', '132456', '2022-10-07 03:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('200917', '20092', '0', '1', '0', '132456', '2022-10-07 03:45:22', '132456', '2022-10-07 03:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20092', '20093', '0', '1', '0', '132456', '2022-10-07 03:45:22', '132456', '2022-10-07 03:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20093', '20094', '0', '1', '0', '132456', '2022-10-07 03:45:22', '132456', '2022-10-07 03:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20094', '200911', '0', '1', '0', '132456', '2022-10-07 03:45:22', '132456', '2022-10-07 03:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('200911', '20099', '0', '1', '0', '132456', '2022-10-07 03:45:22', '132456', '2022-10-07 03:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20099', '200910', '0', '1', '0', '132456', '2022-10-07 03:45:22', '132456', '2022-10-07 03:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2009', '13', '2009', NULL, 132456, '132456', '2022-10-07 03:45:22', '132456', '2022-10-07 03:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2009', '1', '132456', '132456', '2022-10-07 03:45:22', '132456', '2022-10-07 03:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2009', '2', 'dok', '132456', '2022-10-07 03:45:22', '132456', '2022-10-07 03:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2010', '1', '38', '132456', '2022-10-06 14:45:22', '132456', '2022-10-06 14:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2010', '3', '1', '132456', '2022-10-06 14:45:22', '132456', '2022-10-06 14:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2010', '4', '0', '132456', '2022-10-06 14:45:22', '132456', '2022-10-06 14:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2010', '6', 'First2Finish Challenge #2010', '132456', '2022-10-06 14:45:22', '132456', '2022-10-06 14:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2010', '7', '1.0', '132456', '2022-10-06 14:45:22', '132456', '2022-10-06 14:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2010', '9', 'On', '132456', '2022-10-06 14:45:22', '132456', '2022-10-06 14:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2010', '10', 'On', '132456', '2022-10-06 14:45:22', '132456', '2022-10-06 14:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2010', '11', 'On', '132456', '2022-10-06 14:45:22', '132456', '2022-10-06 14:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2010', '12', 'Yes', '132456', '2022-10-06 14:45:22', '132456', '2022-10-06 14:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2010', '13', 'Yes', '132456', '2022-10-06 14:45:22', '132456', '2022-10-06 14:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2010', '14', 'Open', '132456', '2022-10-06 14:45:22', '132456', '2022-10-06 14:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2010', '16', '0.0', '132456', '2022-10-06 14:45:22', '132456', '2022-10-06 14:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2010', '26', 'On', '132456', '2022-10-06 14:45:22', '132456', '2022-10-06 14:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2010', '29', 'On', '132456', '2022-10-06 14:45:22', '132456', '2022-10-06 14:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2010', '42', 'true', '132456', '2022-10-06 14:45:22', '132456', '2022-10-06 14:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2010', '43', 'true', '132456', '2022-10-06 14:45:22', '132456', '2022-10-06 14:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20101', '2010', '1', '2', '2022-10-06 14:45:22', '2022-10-06 14:45:22', '2022-11-05 14:45:22', NULL, NULL, '2592000000.0', '132456', '2022-10-06 14:45:22', '132456', '2022-10-06 14:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20102', '2010', '2', '2', '2022-10-06 14:45:22', '2022-10-06 14:45:22', '2022-11-05 14:45:22', NULL, NULL, '2592000000.0', '132456', '2022-10-06 14:45:22', '132456', '2022-10-06 14:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('201018', '2010', '18', '3', '2022-10-07 14:45:22', '2022-10-07 14:45:22', '2022-10-08 14:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-06 14:45:22', '132456', '2022-10-06 14:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20101', '2', '0', '132456', '2022-10-06 14:45:22', '132456', '2022-10-06 14:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20101', '5', 'No', '132456', '2022-10-06 14:45:22', '132456', '2022-10-06 14:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20102', '3', '0', '132456', '2022-10-06 14:45:22', '132456', '2022-10-06 14:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20102', '5', 'No', '132456', '2022-10-06 14:45:22', '132456', '2022-10-06 14:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('201018', '1', '30000419', '132456', '2022-10-06 14:45:22', '132456', '2022-10-06 14:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20101', '20102', '1', '1', '0', '132456', '2022-10-06 14:45:22', '132456', '2022-10-06 14:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20102', '201018', '0', '1', '0', '132456', '2022-10-06 14:45:22', '132456', '2022-10-06 14:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2010', '13', '2010', NULL, 132456, '132456', '2022-10-06 14:45:22', '132456', '2022-10-06 14:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2010', '1', '132456', '132456', '2022-10-06 14:45:22', '132456', '2022-10-06 14:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2010', '2', 'dok', '132456', '2022-10-06 14:45:22', '132456', '2022-10-06 14:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2011', '1', '17', '132456', '2022-10-07 19:45:22', '132456', '2022-10-07 19:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2011', '3', '1', '132456', '2022-10-07 19:45:22', '132456', '2022-10-07 19:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2011', '4', '0', '132456', '2022-10-07 19:45:22', '132456', '2022-10-07 19:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2011', '6', 'Design 2 Round Challenge #2011', '132456', '2022-10-07 19:45:22', '132456', '2022-10-07 19:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2011', '7', '1.0', '132456', '2022-10-07 19:45:22', '132456', '2022-10-07 19:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2011', '9', 'On', '132456', '2022-10-07 19:45:22', '132456', '2022-10-07 19:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2011', '10', 'On', '132456', '2022-10-07 19:45:22', '132456', '2022-10-07 19:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2011', '11', 'On', '132456', '2022-10-07 19:45:22', '132456', '2022-10-07 19:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2011', '12', 'Yes', '132456', '2022-10-07 19:45:22', '132456', '2022-10-07 19:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2011', '13', 'Yes', '132456', '2022-10-07 19:45:22', '132456', '2022-10-07 19:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2011', '14', 'Open', '132456', '2022-10-07 19:45:22', '132456', '2022-10-07 19:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2011', '16', '0.0', '132456', '2022-10-07 19:45:22', '132456', '2022-10-07 19:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2011', '26', 'On', '132456', '2022-10-07 19:45:22', '132456', '2022-10-07 19:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2011', '29', 'On', '132456', '2022-10-07 19:45:22', '132456', '2022-10-07 19:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2011', '42', 'true', '132456', '2022-10-07 19:45:22', '132456', '2022-10-07 19:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2011', '43', 'true', '132456', '2022-10-07 19:45:22', '132456', '2022-10-07 19:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('201113', '2011', '13', '3', '2022-10-07 19:45:22', '2022-10-07 19:45:22', '2022-10-07 19:45:22', NULL, NULL, '0.0', '132456', '2022-10-07 19:45:22', '132456', '2022-10-07 19:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('201114', '2011', '14', '3', '2022-10-07 19:45:22', '2022-10-07 19:45:22', '2022-10-07 20:45:22', NULL, NULL, '3600000.0', '132456', '2022-10-07 19:45:22', '132456', '2022-10-07 19:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20111', '2011', '1', '3', '2022-10-07 20:45:22', '2022-10-07 20:45:22', '2022-10-09 20:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-07 19:45:22', '132456', '2022-10-07 19:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('201115', '2011', '15', '3', '2022-10-07 20:45:22', '2022-10-07 20:45:22', '2022-10-09 20:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-07 19:45:22', '132456', '2022-10-07 19:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('201116', '2011', '16', '3', '2022-10-09 20:45:22', '2022-10-09 20:45:22', '2022-10-10 02:45:22', NULL, NULL, '21600000.0', '132456', '2022-10-07 19:45:22', '132456', '2022-10-07 19:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('201117', '2011', '17', '2', '2022-10-10 02:45:22', '2022-10-10 02:45:22', '2022-10-11 02:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-07 19:45:22', '132456', '2022-10-07 19:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20112', '2011', '2', '2', '2022-10-07 20:45:22', '2022-10-07 20:45:22', '2022-10-11 20:45:22', NULL, NULL, '345600000.0', '132456', '2022-10-07 19:45:22', '132456', '2022-10-07 19:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20113', '2011', '3', '1', '2022-10-11 20:45:22', '2022-10-11 20:45:22', '2022-10-12 08:45:22', NULL, NULL, '43200000.0', '132456', '2022-10-07 19:45:22', '132456', '2022-10-07 19:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20114', '2011', '4', '1', '2022-10-12 08:45:22', '2022-10-12 08:45:22', '2022-10-12 20:45:22', NULL, NULL, '43200000.0', '132456', '2022-10-07 19:45:22', '132456', '2022-10-07 19:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('201111', '2011', '11', '1', '2022-10-12 20:45:22', '2022-10-12 20:45:22', '2022-10-13 08:45:22', NULL, NULL, '43200000.0', '132456', '2022-10-07 19:45:22', '132456', '2022-10-07 19:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20119', '2011', '9', '1', '2022-10-13 08:45:22', '2022-10-13 08:45:22', '2022-10-14 08:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-07 19:45:22', '132456', '2022-10-07 19:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('201110', '2011', '10', '1', '2022-10-14 08:45:22', '2022-10-14 08:45:22', '2022-10-14 14:45:22', NULL, NULL, '21600000.0', '132456', '2022-10-07 19:45:22', '132456', '2022-10-07 19:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('201113', '3', '0', '132456', '2022-10-07 19:45:22', '132456', '2022-10-07 19:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('201113', '5', 'No', '132456', '2022-10-07 19:45:22', '132456', '2022-10-07 19:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('201114', '1', '30000722', '132456', '2022-10-07 19:45:22', '132456', '2022-10-07 19:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('201114', '6', '3', '132456', '2022-10-07 19:45:22', '132456', '2022-10-07 19:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20111', '2', '0', '132456', '2022-10-07 19:45:22', '132456', '2022-10-07 19:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20111', '5', 'No', '132456', '2022-10-07 19:45:22', '132456', '2022-10-07 19:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('201115', '3', '0', '132456', '2022-10-07 19:45:22', '132456', '2022-10-07 19:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('201115', '5', 'No', '132456', '2022-10-07 19:45:22', '132456', '2022-10-07 19:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('201116', '1', '30000416', '132456', '2022-10-07 19:45:22', '132456', '2022-10-07 19:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('201117', '1', '30000417', '132456', '2022-10-07 19:45:22', '132456', '2022-10-07 19:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('201117', '6', '3', '132456', '2022-10-07 19:45:22', '132456', '2022-10-07 19:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20112', '3', '0', '132456', '2022-10-07 19:45:22', '132456', '2022-10-07 19:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20112', '5', 'No', '132456', '2022-10-07 19:45:22', '132456', '2022-10-07 19:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20113', '1', '30000410', '132456', '2022-10-07 19:45:22', '132456', '2022-10-07 19:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20114', '5', 'No', '132456', '2022-10-07 19:45:22', '132456', '2022-10-07 19:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20114', '1', '30000411', '132456', '2022-10-07 19:45:22', '132456', '2022-10-07 19:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20114', '6', '3', '132456', '2022-10-07 19:45:22', '132456', '2022-10-07 19:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('201111', '1', '30000720', '132456', '2022-10-07 19:45:22', '132456', '2022-10-07 19:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20119', '5', 'No', '132456', '2022-10-07 19:45:22', '132456', '2022-10-07 19:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('201110', '5', 'No', '132456', '2022-10-07 19:45:22', '132456', '2022-10-07 19:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('201113', '201114', '1', '1', '0', '132456', '2022-10-07 19:45:22', '132456', '2022-10-07 19:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('201114', '20111', '0', '1', '0', '132456', '2022-10-07 19:45:22', '132456', '2022-10-07 19:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20111', '201115', '0', '1', '0', '132456', '2022-10-07 19:45:22', '132456', '2022-10-07 19:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('201115', '201116', '0', '1', '0', '132456', '2022-10-07 19:45:22', '132456', '2022-10-07 19:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('201116', '201117', '0', '1', '0', '132456', '2022-10-07 19:45:22', '132456', '2022-10-07 19:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('201117', '20112', '0', '1', '0', '132456', '2022-10-07 19:45:22', '132456', '2022-10-07 19:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20112', '20113', '0', '1', '0', '132456', '2022-10-07 19:45:22', '132456', '2022-10-07 19:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20113', '20114', '0', '1', '0', '132456', '2022-10-07 19:45:22', '132456', '2022-10-07 19:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20114', '201111', '0', '1', '0', '132456', '2022-10-07 19:45:22', '132456', '2022-10-07 19:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('201111', '20119', '0', '1', '0', '132456', '2022-10-07 19:45:22', '132456', '2022-10-07 19:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20119', '201110', '0', '1', '0', '132456', '2022-10-07 19:45:22', '132456', '2022-10-07 19:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2011', '13', '2011', NULL, 132456, '132456', '2022-10-07 19:45:22', '132456', '2022-10-07 19:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2011', '1', '132456', '132456', '2022-10-07 19:45:22', '132456', '2022-10-07 19:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2011', '2', 'dok', '132456', '2022-10-07 19:45:22', '132456', '2022-10-07 19:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2012', '1', '17', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2012', '3', '1', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2012', '4', '0', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2012', '6', 'Design 2 Round Challenge #2012', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2012', '7', '1.0', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2012', '9', 'On', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2012', '10', 'On', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2012', '11', 'On', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2012', '12', 'Yes', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2012', '13', 'Yes', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2012', '14', 'Open', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2012', '16', '0.0', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2012', '26', 'On', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2012', '29', 'On', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2012', '42', 'true', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2012', '43', 'true', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('201213', '2012', '13', '3', '2022-10-07 08:45:22', '2022-10-07 08:45:22', '2022-10-07 08:45:22', NULL, NULL, '0.0', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('201214', '2012', '14', '3', '2022-10-07 08:45:22', '2022-10-07 08:45:22', '2022-10-07 09:45:22', NULL, NULL, '3600000.0', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20121', '2012', '1', '3', '2022-10-07 09:45:22', '2022-10-07 09:45:22', '2022-10-09 09:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('201215', '2012', '15', '3', '2022-10-07 09:45:22', '2022-10-07 09:45:22', '2022-10-09 09:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('201216', '2012', '16', '3', '2022-10-09 09:45:22', '2022-10-09 09:45:22', '2022-10-09 15:45:22', NULL, NULL, '21600000.0', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('201217', '2012', '17', '3', '2022-10-09 15:45:22', '2022-10-09 15:45:22', '2022-10-10 15:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20122', '2012', '2', '2', '2022-10-07 09:45:22', '2022-10-07 09:45:22', '2022-10-11 09:45:22', NULL, NULL, '345600000.0', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20123', '2012', '3', '1', '2022-10-11 09:45:22', '2022-10-11 09:45:22', '2022-10-11 21:45:22', NULL, NULL, '43200000.0', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20124', '2012', '4', '1', '2022-10-11 21:45:22', '2022-10-11 21:45:22', '2022-10-12 09:45:22', NULL, NULL, '43200000.0', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('201211', '2012', '11', '1', '2022-10-12 09:45:22', '2022-10-12 09:45:22', '2022-10-12 21:45:22', NULL, NULL, '43200000.0', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20129', '2012', '9', '1', '2022-10-12 21:45:22', '2022-10-12 21:45:22', '2022-10-13 21:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('201210', '2012', '10', '1', '2022-10-13 21:45:22', '2022-10-13 21:45:22', '2022-10-14 03:45:22', NULL, NULL, '21600000.0', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('201213', '3', '0', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('201213', '5', 'No', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('201214', '1', '30000722', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('201214', '6', '3', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20121', '2', '0', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20121', '5', 'No', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('201215', '3', '0', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('201215', '5', 'No', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('201216', '1', '30000416', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('201217', '1', '30000417', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('201217', '6', '3', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20122', '3', '0', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20122', '5', 'No', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20123', '1', '30000410', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20124', '5', 'No', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20124', '1', '30000411', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20124', '6', '3', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('201211', '1', '30000720', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20129', '5', 'No', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('201210', '5', 'No', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('201213', '201214', '1', '1', '0', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('201214', '20121', '0', '1', '0', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20121', '201215', '0', '1', '0', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('201215', '201216', '0', '1', '0', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('201216', '201217', '0', '1', '0', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('201217', '20122', '0', '1', '0', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20122', '20123', '0', '1', '0', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20123', '20124', '0', '1', '0', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20124', '201211', '0', '1', '0', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('201211', '20129', '0', '1', '0', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20129', '201210', '0', '1', '0', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2012', '13', '2012', NULL, 132456, '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2012', '1', '132456', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2012', '2', 'dok', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2013', '1', '37', '132456', '2022-10-09 01:45:22', '132456', '2022-10-09 01:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2013', '3', '1', '132456', '2022-10-09 01:45:22', '132456', '2022-10-09 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2013', '4', '0', '132456', '2022-10-09 01:45:22', '132456', '2022-10-09 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2013', '6', 'Data Science Challenge #2013', '132456', '2022-10-09 01:45:22', '132456', '2022-10-09 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2013', '7', '1.0', '132456', '2022-10-09 01:45:22', '132456', '2022-10-09 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2013', '9', 'On', '132456', '2022-10-09 01:45:22', '132456', '2022-10-09 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2013', '10', 'On', '132456', '2022-10-09 01:45:22', '132456', '2022-10-09 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2013', '11', 'On', '132456', '2022-10-09 01:45:22', '132456', '2022-10-09 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2013', '12', 'Yes', '132456', '2022-10-09 01:45:22', '132456', '2022-10-09 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2013', '13', 'Yes', '132456', '2022-10-09 01:45:22', '132456', '2022-10-09 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2013', '14', 'Open', '132456', '2022-10-09 01:45:22', '132456', '2022-10-09 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2013', '16', '0.0', '132456', '2022-10-09 01:45:22', '132456', '2022-10-09 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2013', '26', 'On', '132456', '2022-10-09 01:45:22', '132456', '2022-10-09 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2013', '29', 'On', '132456', '2022-10-09 01:45:22', '132456', '2022-10-09 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2013', '42', 'true', '132456', '2022-10-09 01:45:22', '132456', '2022-10-09 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2013', '43', 'true', '132456', '2022-10-09 01:45:22', '132456', '2022-10-09 01:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20131', '2013', '1', '3', '2022-10-09 01:45:22', '2022-10-09 01:45:22', '2022-10-11 01:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-09 01:45:22', '132456', '2022-10-09 01:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20132', '2013', '2', '3', '2022-10-09 01:45:22', '2022-10-09 01:45:22', '2022-10-11 01:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-09 01:45:22', '132456', '2022-10-09 01:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20134', '2013', '4', '2', '2022-10-11 01:45:22', '2022-10-11 01:45:22', '2022-10-12 01:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-09 01:45:22', '132456', '2022-10-09 01:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20131', '2', '0', '132456', '2022-10-09 01:45:22', '132456', '2022-10-09 01:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20131', '5', 'No', '132456', '2022-10-09 01:45:22', '132456', '2022-10-09 01:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20132', '3', '0', '132456', '2022-10-09 01:45:22', '132456', '2022-10-09 01:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20132', '5', 'No', '132456', '2022-10-09 01:45:22', '132456', '2022-10-09 01:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20134', '5', 'No', '132456', '2022-10-09 01:45:22', '132456', '2022-10-09 01:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20134', '1', '30000413', '132456', '2022-10-09 01:45:22', '132456', '2022-10-09 01:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20131', '20132', '1', '1', '0', '132456', '2022-10-09 01:45:22', '132456', '2022-10-09 01:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20132', '20134', '0', '1', '0', '132456', '2022-10-09 01:45:22', '132456', '2022-10-09 01:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2013', '13', '2013', NULL, 132456, '132456', '2022-10-09 01:45:22', '132456', '2022-10-09 01:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2013', '1', '132456', '132456', '2022-10-09 01:45:22', '132456', '2022-10-09 01:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2013', '2', 'dok', '132456', '2022-10-09 01:45:22', '132456', '2022-10-09 01:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2014', '1', '17', '132456', '2022-10-05 11:45:22', '132456', '2022-10-05 11:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2014', '3', '1', '132456', '2022-10-05 11:45:22', '132456', '2022-10-05 11:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2014', '4', '0', '132456', '2022-10-05 11:45:22', '132456', '2022-10-05 11:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2014', '6', 'Design 2 Round Challenge #2014', '132456', '2022-10-05 11:45:22', '132456', '2022-10-05 11:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2014', '7', '1.0', '132456', '2022-10-05 11:45:22', '132456', '2022-10-05 11:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2014', '9', 'On', '132456', '2022-10-05 11:45:22', '132456', '2022-10-05 11:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2014', '10', 'On', '132456', '2022-10-05 11:45:22', '132456', '2022-10-05 11:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2014', '11', 'On', '132456', '2022-10-05 11:45:22', '132456', '2022-10-05 11:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2014', '12', 'Yes', '132456', '2022-10-05 11:45:22', '132456', '2022-10-05 11:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2014', '13', 'Yes', '132456', '2022-10-05 11:45:22', '132456', '2022-10-05 11:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2014', '14', 'Open', '132456', '2022-10-05 11:45:22', '132456', '2022-10-05 11:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2014', '16', '0.0', '132456', '2022-10-05 11:45:22', '132456', '2022-10-05 11:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2014', '26', 'On', '132456', '2022-10-05 11:45:22', '132456', '2022-10-05 11:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2014', '29', 'On', '132456', '2022-10-05 11:45:22', '132456', '2022-10-05 11:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2014', '42', 'true', '132456', '2022-10-05 11:45:22', '132456', '2022-10-05 11:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2014', '43', 'true', '132456', '2022-10-05 11:45:22', '132456', '2022-10-05 11:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('201413', '2014', '13', '3', '2022-10-05 11:45:22', '2022-10-05 11:45:22', '2022-10-05 11:45:22', NULL, NULL, '0.0', '132456', '2022-10-05 11:45:22', '132456', '2022-10-05 11:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('201414', '2014', '14', '3', '2022-10-05 11:45:22', '2022-10-05 11:45:22', '2022-10-05 12:45:22', NULL, NULL, '3600000.0', '132456', '2022-10-05 11:45:22', '132456', '2022-10-05 11:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20141', '2014', '1', '3', '2022-10-05 12:45:22', '2022-10-05 12:45:22', '2022-10-07 12:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-05 11:45:22', '132456', '2022-10-05 11:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('201415', '2014', '15', '3', '2022-10-05 12:45:22', '2022-10-05 12:45:22', '2022-10-07 12:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-05 11:45:22', '132456', '2022-10-05 11:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('201416', '2014', '16', '3', '2022-10-07 12:45:22', '2022-10-07 12:45:22', '2022-10-07 18:45:22', NULL, NULL, '21600000.0', '132456', '2022-10-05 11:45:22', '132456', '2022-10-05 11:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('201417', '2014', '17', '3', '2022-10-07 18:45:22', '2022-10-07 18:45:22', '2022-10-08 18:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-05 11:45:22', '132456', '2022-10-05 11:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20142', '2014', '2', '3', '2022-10-05 12:45:22', '2022-10-05 12:45:22', '2022-10-09 12:45:22', NULL, NULL, '345600000.0', '132456', '2022-10-05 11:45:22', '132456', '2022-10-05 11:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20143', '2014', '3', '3', '2022-10-09 12:45:22', '2022-10-09 12:45:22', '2022-10-10 00:45:22', NULL, NULL, '43200000.0', '132456', '2022-10-05 11:45:22', '132456', '2022-10-05 11:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20144', '2014', '4', '3', '2022-10-10 00:45:22', '2022-10-10 00:45:22', '2022-10-10 12:45:22', NULL, NULL, '43200000.0', '132456', '2022-10-05 11:45:22', '132456', '2022-10-05 11:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('201411', '2014', '11', '3', '2022-10-10 12:45:22', '2022-10-10 12:45:22', '2022-10-11 00:45:22', NULL, NULL, '43200000.0', '132456', '2022-10-05 11:45:22', '132456', '2022-10-05 11:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20149', '2014', '9', '2', '2022-10-11 00:45:22', '2022-10-11 00:45:22', '2022-10-12 00:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-05 11:45:22', '132456', '2022-10-05 11:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('201410', '2014', '10', '1', '2022-10-12 00:45:22', '2022-10-12 00:45:22', '2022-10-12 06:45:22', NULL, NULL, '21600000.0', '132456', '2022-10-05 11:45:22', '132456', '2022-10-05 11:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('201413', '3', '0', '132456', '2022-10-05 11:45:22', '132456', '2022-10-05 11:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('201413', '5', 'No', '132456', '2022-10-05 11:45:22', '132456', '2022-10-05 11:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('201414', '1', '30000722', '132456', '2022-10-05 11:45:22', '132456', '2022-10-05 11:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('201414', '6', '3', '132456', '2022-10-05 11:45:22', '132456', '2022-10-05 11:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20141', '2', '0', '132456', '2022-10-05 11:45:22', '132456', '2022-10-05 11:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20141', '5', 'No', '132456', '2022-10-05 11:45:22', '132456', '2022-10-05 11:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('201415', '3', '0', '132456', '2022-10-05 11:45:22', '132456', '2022-10-05 11:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('201415', '5', 'No', '132456', '2022-10-05 11:45:22', '132456', '2022-10-05 11:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('201416', '1', '30000416', '132456', '2022-10-05 11:45:22', '132456', '2022-10-05 11:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('201417', '1', '30000417', '132456', '2022-10-05 11:45:22', '132456', '2022-10-05 11:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('201417', '6', '3', '132456', '2022-10-05 11:45:22', '132456', '2022-10-05 11:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20142', '3', '0', '132456', '2022-10-05 11:45:22', '132456', '2022-10-05 11:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20142', '5', 'No', '132456', '2022-10-05 11:45:22', '132456', '2022-10-05 11:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20143', '1', '30000410', '132456', '2022-10-05 11:45:22', '132456', '2022-10-05 11:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20144', '5', 'No', '132456', '2022-10-05 11:45:22', '132456', '2022-10-05 11:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20144', '1', '30000411', '132456', '2022-10-05 11:45:22', '132456', '2022-10-05 11:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20144', '6', '3', '132456', '2022-10-05 11:45:22', '132456', '2022-10-05 11:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('201411', '1', '30000720', '132456', '2022-10-05 11:45:22', '132456', '2022-10-05 11:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20149', '5', 'No', '132456', '2022-10-05 11:45:22', '132456', '2022-10-05 11:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('201410', '5', 'No', '132456', '2022-10-05 11:45:22', '132456', '2022-10-05 11:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('201413', '201414', '1', '1', '0', '132456', '2022-10-05 11:45:22', '132456', '2022-10-05 11:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('201414', '20141', '0', '1', '0', '132456', '2022-10-05 11:45:22', '132456', '2022-10-05 11:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20141', '201415', '0', '1', '0', '132456', '2022-10-05 11:45:22', '132456', '2022-10-05 11:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('201415', '201416', '0', '1', '0', '132456', '2022-10-05 11:45:22', '132456', '2022-10-05 11:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('201416', '201417', '0', '1', '0', '132456', '2022-10-05 11:45:22', '132456', '2022-10-05 11:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('201417', '20142', '0', '1', '0', '132456', '2022-10-05 11:45:22', '132456', '2022-10-05 11:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20142', '20143', '0', '1', '0', '132456', '2022-10-05 11:45:22', '132456', '2022-10-05 11:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20143', '20144', '0', '1', '0', '132456', '2022-10-05 11:45:22', '132456', '2022-10-05 11:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20144', '201411', '0', '1', '0', '132456', '2022-10-05 11:45:22', '132456', '2022-10-05 11:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('201411', '20149', '0', '1', '0', '132456', '2022-10-05 11:45:22', '132456', '2022-10-05 11:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20149', '201410', '0', '1', '0', '132456', '2022-10-05 11:45:22', '132456', '2022-10-05 11:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2014', '13', '2014', NULL, 132456, '132456', '2022-10-05 11:45:22', '132456', '2022-10-05 11:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2014', '1', '132456', '132456', '2022-10-05 11:45:22', '132456', '2022-10-05 11:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2014', '2', 'dok', '132456', '2022-10-05 11:45:22', '132456', '2022-10-05 11:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2015', '1', '9', '132456', '2022-10-08 01:45:22', '132456', '2022-10-08 01:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2015', '3', '1', '132456', '2022-10-08 01:45:22', '132456', '2022-10-08 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2015', '4', '0', '132456', '2022-10-08 01:45:22', '132456', '2022-10-08 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2015', '6', 'QA Challenge #2015', '132456', '2022-10-08 01:45:22', '132456', '2022-10-08 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2015', '7', '1.0', '132456', '2022-10-08 01:45:22', '132456', '2022-10-08 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2015', '9', 'On', '132456', '2022-10-08 01:45:22', '132456', '2022-10-08 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2015', '10', 'On', '132456', '2022-10-08 01:45:22', '132456', '2022-10-08 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2015', '11', 'On', '132456', '2022-10-08 01:45:22', '132456', '2022-10-08 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2015', '12', 'Yes', '132456', '2022-10-08 01:45:22', '132456', '2022-10-08 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2015', '13', 'Yes', '132456', '2022-10-08 01:45:22', '132456', '2022-10-08 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2015', '14', 'Open', '132456', '2022-10-08 01:45:22', '132456', '2022-10-08 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2015', '16', '0.0', '132456', '2022-10-08 01:45:22', '132456', '2022-10-08 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2015', '26', 'On', '132456', '2022-10-08 01:45:22', '132456', '2022-10-08 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2015', '29', 'On', '132456', '2022-10-08 01:45:22', '132456', '2022-10-08 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2015', '42', 'true', '132456', '2022-10-08 01:45:22', '132456', '2022-10-08 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2015', '43', 'true', '132456', '2022-10-08 01:45:22', '132456', '2022-10-08 01:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20151', '2015', '1', '3', '2022-10-08 01:45:22', '2022-10-08 01:45:22', '2022-10-10 01:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-08 01:45:22', '132456', '2022-10-08 01:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20152', '2015', '2', '3', '2022-10-08 01:45:22', '2022-10-08 01:45:22', '2022-10-10 01:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-08 01:45:22', '132456', '2022-10-08 01:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20154', '2015', '4', '3', '2022-10-10 01:45:22', '2022-10-10 01:45:22', '2022-10-11 01:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-08 01:45:22', '132456', '2022-10-08 01:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20151', '2', '0', '132456', '2022-10-08 01:45:22', '132456', '2022-10-08 01:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20151', '5', 'No', '132456', '2022-10-08 01:45:22', '132456', '2022-10-08 01:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20152', '3', '0', '132456', '2022-10-08 01:45:22', '132456', '2022-10-08 01:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20152', '5', 'No', '132456', '2022-10-08 01:45:22', '132456', '2022-10-08 01:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20154', '5', 'No', '132456', '2022-10-08 01:45:22', '132456', '2022-10-08 01:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20154', '1', '30000413', '132456', '2022-10-08 01:45:22', '132456', '2022-10-08 01:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20151', '20152', '1', '1', '0', '132456', '2022-10-08 01:45:22', '132456', '2022-10-08 01:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20152', '20154', '0', '1', '0', '132456', '2022-10-08 01:45:22', '132456', '2022-10-08 01:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2015', '13', '2015', NULL, 132456, '132456', '2022-10-08 01:45:22', '132456', '2022-10-08 01:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2015', '1', '132456', '132456', '2022-10-08 01:45:22', '132456', '2022-10-08 01:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2015', '2', 'dok', '132456', '2022-10-08 01:45:22', '132456', '2022-10-08 01:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2016', '1', '40', '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2016', '3', '1', '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2016', '4', '0', '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2016', '6', 'First2Finish Design Challenge #2016', '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2016', '7', '1.0', '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2016', '9', 'On', '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2016', '10', 'On', '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2016', '11', 'On', '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2016', '12', 'Yes', '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2016', '13', 'Yes', '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2016', '14', 'Open', '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2016', '16', '0.0', '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2016', '26', 'On', '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2016', '29', 'On', '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2016', '42', 'true', '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2016', '43', 'true', '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20161', '2016', '1', '2', '2022-10-09 21:45:22', '2022-10-09 21:45:22', '2022-11-08 21:45:22', NULL, NULL, '2592000000.0', '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20162', '2016', '2', '2', '2022-10-09 21:45:22', '2022-10-09 21:45:22', '2022-11-08 21:45:22', NULL, NULL, '2592000000.0', '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('201618', '2016', '18', '2', '2022-10-10 21:45:22', '2022-10-10 21:45:22', '2022-10-11 21:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20161', '2', '0', '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20161', '5', 'No', '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20162', '3', '0', '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20162', '5', 'No', '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('201618', '1', '30000419', '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20161', '20162', '1', '1', '0', '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20162', '201618', '0', '1', '0', '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2016', '13', '2016', NULL, 132456, '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2016', '1', '132456', '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2016', '2', 'dok', '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2017', '1', '40', '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2017', '3', '1', '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2017', '4', '0', '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2017', '6', 'First2Finish Design Challenge #2017', '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2017', '7', '1.0', '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2017', '9', 'On', '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2017', '10', 'On', '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2017', '11', 'On', '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2017', '12', 'Yes', '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2017', '13', 'Yes', '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2017', '14', 'Open', '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2017', '16', '0.0', '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2017', '26', 'On', '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2017', '29', 'On', '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2017', '42', 'true', '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2017', '43', 'true', '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20171', '2017', '1', '2', '2022-10-09 21:45:22', '2022-10-09 21:45:22', '2022-11-08 21:45:22', NULL, NULL, '2592000000.0', '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20172', '2017', '2', '2', '2022-10-09 21:45:22', '2022-10-09 21:45:22', '2022-11-08 21:45:22', NULL, NULL, '2592000000.0', '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('201718', '2017', '18', '2', '2022-10-10 21:45:22', '2022-10-10 21:45:22', '2022-10-11 21:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20171', '2', '0', '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20171', '5', 'No', '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20172', '3', '0', '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20172', '5', 'No', '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('201718', '1', '30000419', '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20171', '20172', '1', '1', '0', '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20172', '201718', '0', '1', '0', '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2017', '13', '2017', NULL, 132456, '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2017', '1', '132456', '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2017', '2', 'dok', '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2018', '1', '39', '132456', '2022-10-07 16:45:22', '132456', '2022-10-07 16:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2018', '3', '1', '132456', '2022-10-07 16:45:22', '132456', '2022-10-07 16:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2018', '4', '0', '132456', '2022-10-07 16:45:22', '132456', '2022-10-07 16:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2018', '6', 'Development Challenge #2018', '132456', '2022-10-07 16:45:22', '132456', '2022-10-07 16:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2018', '7', '1.0', '132456', '2022-10-07 16:45:22', '132456', '2022-10-07 16:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2018', '9', 'On', '132456', '2022-10-07 16:45:22', '132456', '2022-10-07 16:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2018', '10', 'On', '132456', '2022-10-07 16:45:22', '132456', '2022-10-07 16:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2018', '11', 'On', '132456', '2022-10-07 16:45:22', '132456', '2022-10-07 16:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2018', '12', 'Yes', '132456', '2022-10-07 16:45:22', '132456', '2022-10-07 16:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2018', '13', 'Yes', '132456', '2022-10-07 16:45:22', '132456', '2022-10-07 16:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2018', '14', 'Open', '132456', '2022-10-07 16:45:22', '132456', '2022-10-07 16:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2018', '16', '0.0', '132456', '2022-10-07 16:45:22', '132456', '2022-10-07 16:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2018', '26', 'On', '132456', '2022-10-07 16:45:22', '132456', '2022-10-07 16:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2018', '29', 'On', '132456', '2022-10-07 16:45:22', '132456', '2022-10-07 16:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2018', '42', 'true', '132456', '2022-10-07 16:45:22', '132456', '2022-10-07 16:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2018', '43', 'true', '132456', '2022-10-07 16:45:22', '132456', '2022-10-07 16:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20181', '2018', '1', '3', '2022-10-07 16:45:22', '2022-10-07 16:45:22', '2022-10-09 16:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-07 16:45:22', '132456', '2022-10-07 16:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20182', '2018', '2', '3', '2022-10-07 16:45:22', '2022-10-07 16:45:22', '2022-10-09 16:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-07 16:45:22', '132456', '2022-10-07 16:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20184', '2018', '4', '3', '2022-10-09 16:45:22', '2022-10-09 16:45:22', '2022-10-10 16:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-07 16:45:22', '132456', '2022-10-07 16:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20185', '2018', '5', '2', '2022-10-10 16:45:22', '2022-10-10 16:45:22', '2022-10-11 16:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-07 16:45:22', '132456', '2022-10-07 16:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20186', '2018', '6', '1', '2022-10-11 16:45:22', '2022-10-11 16:45:22', '2022-10-12 04:45:22', NULL, NULL, '43200000.0', '132456', '2022-10-07 16:45:22', '132456', '2022-10-07 16:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20181', '2', '0', '132456', '2022-10-07 16:45:22', '132456', '2022-10-07 16:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20181', '5', 'No', '132456', '2022-10-07 16:45:22', '132456', '2022-10-07 16:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20182', '3', '0', '132456', '2022-10-07 16:45:22', '132456', '2022-10-07 16:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20182', '5', 'No', '132456', '2022-10-07 16:45:22', '132456', '2022-10-07 16:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20184', '5', 'No', '132456', '2022-10-07 16:45:22', '132456', '2022-10-07 16:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20184', '1', '30000413', '132456', '2022-10-07 16:45:22', '132456', '2022-10-07 16:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20185', '5', 'No', '132456', '2022-10-07 16:45:22', '132456', '2022-10-07 16:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20185', '4', 'No', '132456', '2022-10-07 16:45:22', '132456', '2022-10-07 16:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20186', '5', 'No', '132456', '2022-10-07 16:45:22', '132456', '2022-10-07 16:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20181', '20182', '1', '1', '0', '132456', '2022-10-07 16:45:22', '132456', '2022-10-07 16:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20182', '20184', '0', '1', '0', '132456', '2022-10-07 16:45:22', '132456', '2022-10-07 16:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20184', '20185', '0', '1', '0', '132456', '2022-10-07 16:45:22', '132456', '2022-10-07 16:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20185', '20186', '0', '1', '0', '132456', '2022-10-07 16:45:22', '132456', '2022-10-07 16:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2018', '13', '2018', NULL, 132456, '132456', '2022-10-07 16:45:22', '132456', '2022-10-07 16:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2018', '1', '132456', '132456', '2022-10-07 16:45:22', '132456', '2022-10-07 16:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2018', '2', 'dok', '132456', '2022-10-07 16:45:22', '132456', '2022-10-07 16:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2019', '1', '17', '132456', '2022-10-06 16:45:22', '132456', '2022-10-06 16:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2019', '3', '1', '132456', '2022-10-06 16:45:22', '132456', '2022-10-06 16:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2019', '4', '0', '132456', '2022-10-06 16:45:22', '132456', '2022-10-06 16:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2019', '6', 'Design Challenge #2019', '132456', '2022-10-06 16:45:22', '132456', '2022-10-06 16:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2019', '7', '1.0', '132456', '2022-10-06 16:45:22', '132456', '2022-10-06 16:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2019', '9', 'On', '132456', '2022-10-06 16:45:22', '132456', '2022-10-06 16:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2019', '10', 'On', '132456', '2022-10-06 16:45:22', '132456', '2022-10-06 16:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2019', '11', 'On', '132456', '2022-10-06 16:45:22', '132456', '2022-10-06 16:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2019', '12', 'Yes', '132456', '2022-10-06 16:45:22', '132456', '2022-10-06 16:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2019', '13', 'Yes', '132456', '2022-10-06 16:45:22', '132456', '2022-10-06 16:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2019', '14', 'Open', '132456', '2022-10-06 16:45:22', '132456', '2022-10-06 16:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2019', '16', '0.0', '132456', '2022-10-06 16:45:22', '132456', '2022-10-06 16:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2019', '26', 'On', '132456', '2022-10-06 16:45:22', '132456', '2022-10-06 16:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2019', '29', 'On', '132456', '2022-10-06 16:45:22', '132456', '2022-10-06 16:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2019', '42', 'true', '132456', '2022-10-06 16:45:22', '132456', '2022-10-06 16:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2019', '43', 'true', '132456', '2022-10-06 16:45:22', '132456', '2022-10-06 16:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20191', '2019', '1', '3', '2022-10-06 16:45:22', '2022-10-06 16:45:22', '2022-10-08 16:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-06 16:45:22', '132456', '2022-10-06 16:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20192', '2019', '2', '3', '2022-10-06 16:45:22', '2022-10-06 16:45:22', '2022-10-08 16:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-06 16:45:22', '132456', '2022-10-06 16:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20193', '2019', '3', '3', '2022-10-08 16:45:22', '2022-10-08 16:45:22', '2022-10-09 04:45:22', NULL, NULL, '43200000.0', '132456', '2022-10-06 16:45:22', '132456', '2022-10-06 16:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20194', '2019', '4', '3', '2022-10-09 04:45:22', '2022-10-09 04:45:22', '2022-10-10 04:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-06 16:45:22', '132456', '2022-10-06 16:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('201911', '2019', '11', '3', '2022-10-10 04:45:22', '2022-10-10 04:45:22', '2022-10-10 16:45:22', NULL, NULL, '43200000.0', '132456', '2022-10-06 16:45:22', '132456', '2022-10-06 16:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20191', '2', '0', '132456', '2022-10-06 16:45:22', '132456', '2022-10-06 16:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20191', '5', 'No', '132456', '2022-10-06 16:45:22', '132456', '2022-10-06 16:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20192', '3', '0', '132456', '2022-10-06 16:45:22', '132456', '2022-10-06 16:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20192', '5', 'No', '132456', '2022-10-06 16:45:22', '132456', '2022-10-06 16:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20193', '1', '30000410', '132456', '2022-10-06 16:45:22', '132456', '2022-10-06 16:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20194', '5', 'No', '132456', '2022-10-06 16:45:22', '132456', '2022-10-06 16:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20194', '1', '30000411', '132456', '2022-10-06 16:45:22', '132456', '2022-10-06 16:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('201911', '1', '30000720', '132456', '2022-10-06 16:45:22', '132456', '2022-10-06 16:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20191', '20192', '1', '1', '0', '132456', '2022-10-06 16:45:22', '132456', '2022-10-06 16:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20192', '20193', '0', '1', '0', '132456', '2022-10-06 16:45:22', '132456', '2022-10-06 16:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20193', '20194', '0', '1', '0', '132456', '2022-10-06 16:45:22', '132456', '2022-10-06 16:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20194', '201911', '0', '1', '0', '132456', '2022-10-06 16:45:22', '132456', '2022-10-06 16:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2019', '13', '2019', NULL, 132456, '132456', '2022-10-06 16:45:22', '132456', '2022-10-06 16:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2019', '1', '132456', '132456', '2022-10-06 16:45:22', '132456', '2022-10-06 16:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2019', '2', 'dok', '132456', '2022-10-06 16:45:22', '132456', '2022-10-06 16:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2020', '1', '37', '132456', '2022-10-08 16:45:22', '132456', '2022-10-08 16:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2020', '3', '1', '132456', '2022-10-08 16:45:22', '132456', '2022-10-08 16:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2020', '4', '0', '132456', '2022-10-08 16:45:22', '132456', '2022-10-08 16:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2020', '6', 'Data Science Challenge #2020', '132456', '2022-10-08 16:45:22', '132456', '2022-10-08 16:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2020', '7', '1.0', '132456', '2022-10-08 16:45:22', '132456', '2022-10-08 16:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2020', '9', 'On', '132456', '2022-10-08 16:45:22', '132456', '2022-10-08 16:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2020', '10', 'On', '132456', '2022-10-08 16:45:22', '132456', '2022-10-08 16:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2020', '11', 'On', '132456', '2022-10-08 16:45:22', '132456', '2022-10-08 16:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2020', '12', 'Yes', '132456', '2022-10-08 16:45:22', '132456', '2022-10-08 16:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2020', '13', 'Yes', '132456', '2022-10-08 16:45:22', '132456', '2022-10-08 16:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2020', '14', 'Open', '132456', '2022-10-08 16:45:22', '132456', '2022-10-08 16:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2020', '16', '0.0', '132456', '2022-10-08 16:45:22', '132456', '2022-10-08 16:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2020', '26', 'On', '132456', '2022-10-08 16:45:22', '132456', '2022-10-08 16:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2020', '29', 'On', '132456', '2022-10-08 16:45:22', '132456', '2022-10-08 16:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2020', '42', 'true', '132456', '2022-10-08 16:45:22', '132456', '2022-10-08 16:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2020', '43', 'true', '132456', '2022-10-08 16:45:22', '132456', '2022-10-08 16:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20201', '2020', '1', '3', '2022-10-08 16:45:22', '2022-10-08 16:45:22', '2022-10-10 16:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-08 16:45:22', '132456', '2022-10-08 16:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20202', '2020', '2', '3', '2022-10-08 16:45:22', '2022-10-08 16:45:22', '2022-10-10 16:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-08 16:45:22', '132456', '2022-10-08 16:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20204', '2020', '4', '2', '2022-10-10 16:45:22', '2022-10-10 16:45:22', '2022-10-11 16:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-08 16:45:22', '132456', '2022-10-08 16:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20201', '2', '0', '132456', '2022-10-08 16:45:22', '132456', '2022-10-08 16:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20201', '5', 'No', '132456', '2022-10-08 16:45:22', '132456', '2022-10-08 16:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20202', '3', '0', '132456', '2022-10-08 16:45:22', '132456', '2022-10-08 16:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20202', '5', 'No', '132456', '2022-10-08 16:45:22', '132456', '2022-10-08 16:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20204', '5', 'No', '132456', '2022-10-08 16:45:22', '132456', '2022-10-08 16:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20204', '1', '30000413', '132456', '2022-10-08 16:45:22', '132456', '2022-10-08 16:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20201', '20202', '1', '1', '0', '132456', '2022-10-08 16:45:22', '132456', '2022-10-08 16:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20202', '20204', '0', '1', '0', '132456', '2022-10-08 16:45:22', '132456', '2022-10-08 16:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2020', '13', '2020', NULL, 132456, '132456', '2022-10-08 16:45:22', '132456', '2022-10-08 16:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2020', '1', '132456', '132456', '2022-10-08 16:45:22', '132456', '2022-10-08 16:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2020', '2', 'dok', '132456', '2022-10-08 16:45:22', '132456', '2022-10-08 16:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2021', '1', '17', '132456', '2022-10-09 19:45:22', '132456', '2022-10-09 19:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2021', '3', '1', '132456', '2022-10-09 19:45:22', '132456', '2022-10-09 19:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2021', '4', '0', '132456', '2022-10-09 19:45:22', '132456', '2022-10-09 19:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2021', '6', 'Design Challenge #2021', '132456', '2022-10-09 19:45:22', '132456', '2022-10-09 19:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2021', '7', '1.0', '132456', '2022-10-09 19:45:22', '132456', '2022-10-09 19:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2021', '9', 'On', '132456', '2022-10-09 19:45:22', '132456', '2022-10-09 19:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2021', '10', 'On', '132456', '2022-10-09 19:45:22', '132456', '2022-10-09 19:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2021', '11', 'On', '132456', '2022-10-09 19:45:22', '132456', '2022-10-09 19:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2021', '12', 'Yes', '132456', '2022-10-09 19:45:22', '132456', '2022-10-09 19:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2021', '13', 'Yes', '132456', '2022-10-09 19:45:22', '132456', '2022-10-09 19:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2021', '14', 'Open', '132456', '2022-10-09 19:45:22', '132456', '2022-10-09 19:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2021', '16', '0.0', '132456', '2022-10-09 19:45:22', '132456', '2022-10-09 19:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2021', '26', 'On', '132456', '2022-10-09 19:45:22', '132456', '2022-10-09 19:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2021', '29', 'On', '132456', '2022-10-09 19:45:22', '132456', '2022-10-09 19:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2021', '42', 'true', '132456', '2022-10-09 19:45:22', '132456', '2022-10-09 19:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2021', '43', 'true', '132456', '2022-10-09 19:45:22', '132456', '2022-10-09 19:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20211', '2021', '1', '2', '2022-10-09 19:45:22', '2022-10-09 19:45:22', '2022-10-11 19:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-09 19:45:22', '132456', '2022-10-09 19:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20212', '2021', '2', '2', '2022-10-09 19:45:22', '2022-10-09 19:45:22', '2022-10-11 19:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-09 19:45:22', '132456', '2022-10-09 19:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20213', '2021', '3', '1', '2022-10-11 19:45:22', '2022-10-11 19:45:22', '2022-10-12 07:45:22', NULL, NULL, '43200000.0', '132456', '2022-10-09 19:45:22', '132456', '2022-10-09 19:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20214', '2021', '4', '1', '2022-10-12 07:45:22', '2022-10-12 07:45:22', '2022-10-13 07:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-09 19:45:22', '132456', '2022-10-09 19:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('202111', '2021', '11', '1', '2022-10-13 07:45:22', '2022-10-13 07:45:22', '2022-10-13 19:45:22', NULL, NULL, '43200000.0', '132456', '2022-10-09 19:45:22', '132456', '2022-10-09 19:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20211', '2', '0', '132456', '2022-10-09 19:45:22', '132456', '2022-10-09 19:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20211', '5', 'No', '132456', '2022-10-09 19:45:22', '132456', '2022-10-09 19:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20212', '3', '0', '132456', '2022-10-09 19:45:22', '132456', '2022-10-09 19:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20212', '5', 'No', '132456', '2022-10-09 19:45:22', '132456', '2022-10-09 19:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20213', '1', '30000410', '132456', '2022-10-09 19:45:22', '132456', '2022-10-09 19:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20214', '5', 'No', '132456', '2022-10-09 19:45:22', '132456', '2022-10-09 19:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20214', '1', '30000411', '132456', '2022-10-09 19:45:22', '132456', '2022-10-09 19:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('202111', '1', '30000720', '132456', '2022-10-09 19:45:22', '132456', '2022-10-09 19:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20211', '20212', '1', '1', '0', '132456', '2022-10-09 19:45:22', '132456', '2022-10-09 19:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20212', '20213', '0', '1', '0', '132456', '2022-10-09 19:45:22', '132456', '2022-10-09 19:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20213', '20214', '0', '1', '0', '132456', '2022-10-09 19:45:22', '132456', '2022-10-09 19:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20214', '202111', '0', '1', '0', '132456', '2022-10-09 19:45:22', '132456', '2022-10-09 19:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2021', '13', '2021', NULL, 132456, '132456', '2022-10-09 19:45:22', '132456', '2022-10-09 19:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2021', '1', '132456', '132456', '2022-10-09 19:45:22', '132456', '2022-10-09 19:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2021', '2', 'dok', '132456', '2022-10-09 19:45:22', '132456', '2022-10-09 19:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2022', '1', '9', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2022', '3', '1', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2022', '4', '0', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2022', '6', 'QA Challenge #2022', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2022', '7', '1.0', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2022', '9', 'On', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2022', '10', 'On', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2022', '11', 'On', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2022', '12', 'Yes', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2022', '13', 'Yes', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2022', '14', 'Open', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2022', '16', '0.0', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2022', '26', 'On', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2022', '29', 'On', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2022', '42', 'true', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2022', '43', 'true', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20221', '2022', '1', '3', '2022-10-07 21:45:22', '2022-10-07 21:45:22', '2022-10-09 21:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20222', '2022', '2', '3', '2022-10-07 21:45:22', '2022-10-07 21:45:22', '2022-10-09 21:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20224', '2022', '4', '3', '2022-10-09 21:45:22', '2022-10-09 21:45:22', '2022-10-10 21:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20221', '2', '0', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20221', '5', 'No', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20222', '3', '0', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20222', '5', 'No', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20224', '5', 'No', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20224', '1', '30000413', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20221', '20222', '1', '1', '0', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20222', '20224', '0', '1', '0', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2022', '13', '2022', NULL, 132456, '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2022', '1', '132456', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2022', '2', 'dok', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2023', '1', '38', '132456', '2022-10-06 20:45:22', '132456', '2022-10-06 20:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2023', '3', '1', '132456', '2022-10-06 20:45:22', '132456', '2022-10-06 20:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2023', '4', '0', '132456', '2022-10-06 20:45:22', '132456', '2022-10-06 20:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2023', '6', 'First2Finish Challenge #2023', '132456', '2022-10-06 20:45:22', '132456', '2022-10-06 20:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2023', '7', '1.0', '132456', '2022-10-06 20:45:22', '132456', '2022-10-06 20:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2023', '9', 'On', '132456', '2022-10-06 20:45:22', '132456', '2022-10-06 20:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2023', '10', 'On', '132456', '2022-10-06 20:45:22', '132456', '2022-10-06 20:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2023', '11', 'On', '132456', '2022-10-06 20:45:22', '132456', '2022-10-06 20:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2023', '12', 'Yes', '132456', '2022-10-06 20:45:22', '132456', '2022-10-06 20:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2023', '13', 'Yes', '132456', '2022-10-06 20:45:22', '132456', '2022-10-06 20:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2023', '14', 'Open', '132456', '2022-10-06 20:45:22', '132456', '2022-10-06 20:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2023', '16', '0.0', '132456', '2022-10-06 20:45:22', '132456', '2022-10-06 20:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2023', '26', 'On', '132456', '2022-10-06 20:45:22', '132456', '2022-10-06 20:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2023', '29', 'On', '132456', '2022-10-06 20:45:22', '132456', '2022-10-06 20:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2023', '42', 'true', '132456', '2022-10-06 20:45:22', '132456', '2022-10-06 20:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2023', '43', 'true', '132456', '2022-10-06 20:45:22', '132456', '2022-10-06 20:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20231', '2023', '1', '2', '2022-10-06 20:45:22', '2022-10-06 20:45:22', '2022-11-05 20:45:22', NULL, NULL, '2592000000.0', '132456', '2022-10-06 20:45:22', '132456', '2022-10-06 20:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20232', '2023', '2', '2', '2022-10-06 20:45:22', '2022-10-06 20:45:22', '2022-11-05 20:45:22', NULL, NULL, '2592000000.0', '132456', '2022-10-06 20:45:22', '132456', '2022-10-06 20:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('202318', '2023', '18', '3', '2022-10-07 20:45:22', '2022-10-07 20:45:22', '2022-10-08 20:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-06 20:45:22', '132456', '2022-10-06 20:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20231', '2', '0', '132456', '2022-10-06 20:45:22', '132456', '2022-10-06 20:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20231', '5', 'No', '132456', '2022-10-06 20:45:22', '132456', '2022-10-06 20:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20232', '3', '0', '132456', '2022-10-06 20:45:22', '132456', '2022-10-06 20:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20232', '5', 'No', '132456', '2022-10-06 20:45:22', '132456', '2022-10-06 20:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('202318', '1', '30000419', '132456', '2022-10-06 20:45:22', '132456', '2022-10-06 20:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20231', '20232', '1', '1', '0', '132456', '2022-10-06 20:45:22', '132456', '2022-10-06 20:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20232', '202318', '0', '1', '0', '132456', '2022-10-06 20:45:22', '132456', '2022-10-06 20:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2023', '13', '2023', NULL, 132456, '132456', '2022-10-06 20:45:22', '132456', '2022-10-06 20:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2023', '1', '132456', '132456', '2022-10-06 20:45:22', '132456', '2022-10-06 20:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2023', '2', 'dok', '132456', '2022-10-06 20:45:22', '132456', '2022-10-06 20:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2024', '1', '17', '132456', '2022-10-07 09:45:22', '132456', '2022-10-07 09:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2024', '3', '1', '132456', '2022-10-07 09:45:22', '132456', '2022-10-07 09:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2024', '4', '0', '132456', '2022-10-07 09:45:22', '132456', '2022-10-07 09:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2024', '6', 'Design 2 Round Challenge #2024', '132456', '2022-10-07 09:45:22', '132456', '2022-10-07 09:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2024', '7', '1.0', '132456', '2022-10-07 09:45:22', '132456', '2022-10-07 09:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2024', '9', 'On', '132456', '2022-10-07 09:45:22', '132456', '2022-10-07 09:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2024', '10', 'On', '132456', '2022-10-07 09:45:22', '132456', '2022-10-07 09:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2024', '11', 'On', '132456', '2022-10-07 09:45:22', '132456', '2022-10-07 09:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2024', '12', 'Yes', '132456', '2022-10-07 09:45:22', '132456', '2022-10-07 09:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2024', '13', 'Yes', '132456', '2022-10-07 09:45:22', '132456', '2022-10-07 09:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2024', '14', 'Open', '132456', '2022-10-07 09:45:22', '132456', '2022-10-07 09:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2024', '16', '0.0', '132456', '2022-10-07 09:45:22', '132456', '2022-10-07 09:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2024', '26', 'On', '132456', '2022-10-07 09:45:22', '132456', '2022-10-07 09:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2024', '29', 'On', '132456', '2022-10-07 09:45:22', '132456', '2022-10-07 09:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2024', '42', 'true', '132456', '2022-10-07 09:45:22', '132456', '2022-10-07 09:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2024', '43', 'true', '132456', '2022-10-07 09:45:22', '132456', '2022-10-07 09:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('202413', '2024', '13', '3', '2022-10-07 09:45:22', '2022-10-07 09:45:22', '2022-10-07 09:45:22', NULL, NULL, '0.0', '132456', '2022-10-07 09:45:22', '132456', '2022-10-07 09:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('202414', '2024', '14', '3', '2022-10-07 09:45:22', '2022-10-07 09:45:22', '2022-10-07 10:45:22', NULL, NULL, '3600000.0', '132456', '2022-10-07 09:45:22', '132456', '2022-10-07 09:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20241', '2024', '1', '3', '2022-10-07 10:45:22', '2022-10-07 10:45:22', '2022-10-09 10:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-07 09:45:22', '132456', '2022-10-07 09:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('202415', '2024', '15', '3', '2022-10-07 10:45:22', '2022-10-07 10:45:22', '2022-10-09 10:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-07 09:45:22', '132456', '2022-10-07 09:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('202416', '2024', '16', '3', '2022-10-09 10:45:22', '2022-10-09 10:45:22', '2022-10-09 16:45:22', NULL, NULL, '21600000.0', '132456', '2022-10-07 09:45:22', '132456', '2022-10-07 09:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('202417', '2024', '17', '3', '2022-10-09 16:45:22', '2022-10-09 16:45:22', '2022-10-10 16:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-07 09:45:22', '132456', '2022-10-07 09:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20242', '2024', '2', '2', '2022-10-07 10:45:22', '2022-10-07 10:45:22', '2022-10-11 10:45:22', NULL, NULL, '345600000.0', '132456', '2022-10-07 09:45:22', '132456', '2022-10-07 09:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20243', '2024', '3', '1', '2022-10-11 10:45:22', '2022-10-11 10:45:22', '2022-10-11 22:45:22', NULL, NULL, '43200000.0', '132456', '2022-10-07 09:45:22', '132456', '2022-10-07 09:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20244', '2024', '4', '1', '2022-10-11 22:45:22', '2022-10-11 22:45:22', '2022-10-12 10:45:22', NULL, NULL, '43200000.0', '132456', '2022-10-07 09:45:22', '132456', '2022-10-07 09:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('202411', '2024', '11', '1', '2022-10-12 10:45:22', '2022-10-12 10:45:22', '2022-10-12 22:45:22', NULL, NULL, '43200000.0', '132456', '2022-10-07 09:45:22', '132456', '2022-10-07 09:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20249', '2024', '9', '1', '2022-10-12 22:45:22', '2022-10-12 22:45:22', '2022-10-13 22:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-07 09:45:22', '132456', '2022-10-07 09:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('202410', '2024', '10', '1', '2022-10-13 22:45:22', '2022-10-13 22:45:22', '2022-10-14 04:45:22', NULL, NULL, '21600000.0', '132456', '2022-10-07 09:45:22', '132456', '2022-10-07 09:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('202413', '3', '0', '132456', '2022-10-07 09:45:22', '132456', '2022-10-07 09:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('202413', '5', 'No', '132456', '2022-10-07 09:45:22', '132456', '2022-10-07 09:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('202414', '1', '30000722', '132456', '2022-10-07 09:45:22', '132456', '2022-10-07 09:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('202414', '6', '3', '132456', '2022-10-07 09:45:22', '132456', '2022-10-07 09:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20241', '2', '0', '132456', '2022-10-07 09:45:22', '132456', '2022-10-07 09:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20241', '5', 'No', '132456', '2022-10-07 09:45:22', '132456', '2022-10-07 09:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('202415', '3', '0', '132456', '2022-10-07 09:45:22', '132456', '2022-10-07 09:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('202415', '5', 'No', '132456', '2022-10-07 09:45:22', '132456', '2022-10-07 09:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('202416', '1', '30000416', '132456', '2022-10-07 09:45:22', '132456', '2022-10-07 09:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('202417', '1', '30000417', '132456', '2022-10-07 09:45:22', '132456', '2022-10-07 09:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('202417', '6', '3', '132456', '2022-10-07 09:45:22', '132456', '2022-10-07 09:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20242', '3', '0', '132456', '2022-10-07 09:45:22', '132456', '2022-10-07 09:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20242', '5', 'No', '132456', '2022-10-07 09:45:22', '132456', '2022-10-07 09:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20243', '1', '30000410', '132456', '2022-10-07 09:45:22', '132456', '2022-10-07 09:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20244', '5', 'No', '132456', '2022-10-07 09:45:22', '132456', '2022-10-07 09:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20244', '1', '30000411', '132456', '2022-10-07 09:45:22', '132456', '2022-10-07 09:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20244', '6', '3', '132456', '2022-10-07 09:45:22', '132456', '2022-10-07 09:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('202411', '1', '30000720', '132456', '2022-10-07 09:45:22', '132456', '2022-10-07 09:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20249', '5', 'No', '132456', '2022-10-07 09:45:22', '132456', '2022-10-07 09:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('202410', '5', 'No', '132456', '2022-10-07 09:45:22', '132456', '2022-10-07 09:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('202413', '202414', '1', '1', '0', '132456', '2022-10-07 09:45:22', '132456', '2022-10-07 09:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('202414', '20241', '0', '1', '0', '132456', '2022-10-07 09:45:22', '132456', '2022-10-07 09:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20241', '202415', '0', '1', '0', '132456', '2022-10-07 09:45:22', '132456', '2022-10-07 09:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('202415', '202416', '0', '1', '0', '132456', '2022-10-07 09:45:22', '132456', '2022-10-07 09:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('202416', '202417', '0', '1', '0', '132456', '2022-10-07 09:45:22', '132456', '2022-10-07 09:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('202417', '20242', '0', '1', '0', '132456', '2022-10-07 09:45:22', '132456', '2022-10-07 09:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20242', '20243', '0', '1', '0', '132456', '2022-10-07 09:45:22', '132456', '2022-10-07 09:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20243', '20244', '0', '1', '0', '132456', '2022-10-07 09:45:22', '132456', '2022-10-07 09:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20244', '202411', '0', '1', '0', '132456', '2022-10-07 09:45:22', '132456', '2022-10-07 09:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('202411', '20249', '0', '1', '0', '132456', '2022-10-07 09:45:22', '132456', '2022-10-07 09:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20249', '202410', '0', '1', '0', '132456', '2022-10-07 09:45:22', '132456', '2022-10-07 09:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2024', '13', '2024', NULL, 132456, '132456', '2022-10-07 09:45:22', '132456', '2022-10-07 09:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2024', '1', '132456', '132456', '2022-10-07 09:45:22', '132456', '2022-10-07 09:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2024', '2', 'dok', '132456', '2022-10-07 09:45:22', '132456', '2022-10-07 09:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2025', '1', '9', '132456', '2022-10-06 20:45:22', '132456', '2022-10-06 20:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2025', '3', '1', '132456', '2022-10-06 20:45:22', '132456', '2022-10-06 20:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2025', '4', '0', '132456', '2022-10-06 20:45:22', '132456', '2022-10-06 20:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2025', '6', 'QA Challenge #2025', '132456', '2022-10-06 20:45:22', '132456', '2022-10-06 20:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2025', '7', '1.0', '132456', '2022-10-06 20:45:22', '132456', '2022-10-06 20:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2025', '9', 'On', '132456', '2022-10-06 20:45:22', '132456', '2022-10-06 20:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2025', '10', 'On', '132456', '2022-10-06 20:45:22', '132456', '2022-10-06 20:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2025', '11', 'On', '132456', '2022-10-06 20:45:22', '132456', '2022-10-06 20:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2025', '12', 'Yes', '132456', '2022-10-06 20:45:22', '132456', '2022-10-06 20:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2025', '13', 'Yes', '132456', '2022-10-06 20:45:22', '132456', '2022-10-06 20:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2025', '14', 'Open', '132456', '2022-10-06 20:45:22', '132456', '2022-10-06 20:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2025', '16', '0.0', '132456', '2022-10-06 20:45:22', '132456', '2022-10-06 20:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2025', '26', 'On', '132456', '2022-10-06 20:45:22', '132456', '2022-10-06 20:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2025', '29', 'On', '132456', '2022-10-06 20:45:22', '132456', '2022-10-06 20:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2025', '42', 'true', '132456', '2022-10-06 20:45:22', '132456', '2022-10-06 20:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2025', '43', 'true', '132456', '2022-10-06 20:45:22', '132456', '2022-10-06 20:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20251', '2025', '1', '3', '2022-10-06 20:45:22', '2022-10-06 20:45:22', '2022-10-08 20:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-06 20:45:22', '132456', '2022-10-06 20:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20252', '2025', '2', '3', '2022-10-06 20:45:22', '2022-10-06 20:45:22', '2022-10-08 20:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-06 20:45:22', '132456', '2022-10-06 20:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20254', '2025', '4', '3', '2022-10-08 20:45:22', '2022-10-08 20:45:22', '2022-10-09 20:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-06 20:45:22', '132456', '2022-10-06 20:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20251', '2', '0', '132456', '2022-10-06 20:45:22', '132456', '2022-10-06 20:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20251', '5', 'No', '132456', '2022-10-06 20:45:22', '132456', '2022-10-06 20:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20252', '3', '0', '132456', '2022-10-06 20:45:22', '132456', '2022-10-06 20:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20252', '5', 'No', '132456', '2022-10-06 20:45:22', '132456', '2022-10-06 20:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20254', '5', 'No', '132456', '2022-10-06 20:45:22', '132456', '2022-10-06 20:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20254', '1', '30000413', '132456', '2022-10-06 20:45:22', '132456', '2022-10-06 20:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20251', '20252', '1', '1', '0', '132456', '2022-10-06 20:45:22', '132456', '2022-10-06 20:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20252', '20254', '0', '1', '0', '132456', '2022-10-06 20:45:22', '132456', '2022-10-06 20:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2025', '13', '2025', NULL, 132456, '132456', '2022-10-06 20:45:22', '132456', '2022-10-06 20:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2025', '1', '132456', '132456', '2022-10-06 20:45:22', '132456', '2022-10-06 20:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2025', '2', 'dok', '132456', '2022-10-06 20:45:22', '132456', '2022-10-06 20:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2026', '1', '38', '132456', '2022-10-05 07:45:22', '132456', '2022-10-05 07:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2026', '3', '1', '132456', '2022-10-05 07:45:22', '132456', '2022-10-05 07:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2026', '4', '0', '132456', '2022-10-05 07:45:22', '132456', '2022-10-05 07:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2026', '6', 'First2Finish Challenge #2026', '132456', '2022-10-05 07:45:22', '132456', '2022-10-05 07:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2026', '7', '1.0', '132456', '2022-10-05 07:45:22', '132456', '2022-10-05 07:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2026', '9', 'On', '132456', '2022-10-05 07:45:22', '132456', '2022-10-05 07:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2026', '10', 'On', '132456', '2022-10-05 07:45:22', '132456', '2022-10-05 07:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2026', '11', 'On', '132456', '2022-10-05 07:45:22', '132456', '2022-10-05 07:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2026', '12', 'Yes', '132456', '2022-10-05 07:45:22', '132456', '2022-10-05 07:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2026', '13', 'Yes', '132456', '2022-10-05 07:45:22', '132456', '2022-10-05 07:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2026', '14', 'Open', '132456', '2022-10-05 07:45:22', '132456', '2022-10-05 07:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2026', '16', '0.0', '132456', '2022-10-05 07:45:22', '132456', '2022-10-05 07:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2026', '26', 'On', '132456', '2022-10-05 07:45:22', '132456', '2022-10-05 07:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2026', '29', 'On', '132456', '2022-10-05 07:45:22', '132456', '2022-10-05 07:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2026', '42', 'true', '132456', '2022-10-05 07:45:22', '132456', '2022-10-05 07:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2026', '43', 'true', '132456', '2022-10-05 07:45:22', '132456', '2022-10-05 07:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20261', '2026', '1', '2', '2022-10-05 07:45:22', '2022-10-05 07:45:22', '2022-11-04 07:45:22', NULL, NULL, '2592000000.0', '132456', '2022-10-05 07:45:22', '132456', '2022-10-05 07:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20262', '2026', '2', '2', '2022-10-05 07:45:22', '2022-10-05 07:45:22', '2022-11-04 07:45:22', NULL, NULL, '2592000000.0', '132456', '2022-10-05 07:45:22', '132456', '2022-10-05 07:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('202618', '2026', '18', '3', '2022-10-06 07:45:22', '2022-10-06 07:45:22', '2022-10-07 07:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-05 07:45:22', '132456', '2022-10-05 07:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20261', '2', '0', '132456', '2022-10-05 07:45:22', '132456', '2022-10-05 07:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20261', '5', 'No', '132456', '2022-10-05 07:45:22', '132456', '2022-10-05 07:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20262', '3', '0', '132456', '2022-10-05 07:45:22', '132456', '2022-10-05 07:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20262', '5', 'No', '132456', '2022-10-05 07:45:22', '132456', '2022-10-05 07:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('202618', '1', '30000419', '132456', '2022-10-05 07:45:22', '132456', '2022-10-05 07:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20261', '20262', '1', '1', '0', '132456', '2022-10-05 07:45:22', '132456', '2022-10-05 07:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20262', '202618', '0', '1', '0', '132456', '2022-10-05 07:45:22', '132456', '2022-10-05 07:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2026', '13', '2026', NULL, 132456, '132456', '2022-10-05 07:45:22', '132456', '2022-10-05 07:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2026', '1', '132456', '132456', '2022-10-05 07:45:22', '132456', '2022-10-05 07:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2026', '2', 'dok', '132456', '2022-10-05 07:45:22', '132456', '2022-10-05 07:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2027', '1', '37', '132456', '2022-10-07 19:45:22', '132456', '2022-10-07 19:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2027', '3', '1', '132456', '2022-10-07 19:45:22', '132456', '2022-10-07 19:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2027', '4', '0', '132456', '2022-10-07 19:45:22', '132456', '2022-10-07 19:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2027', '6', 'Data Science Challenge #2027', '132456', '2022-10-07 19:45:22', '132456', '2022-10-07 19:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2027', '7', '1.0', '132456', '2022-10-07 19:45:22', '132456', '2022-10-07 19:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2027', '9', 'On', '132456', '2022-10-07 19:45:22', '132456', '2022-10-07 19:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2027', '10', 'On', '132456', '2022-10-07 19:45:22', '132456', '2022-10-07 19:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2027', '11', 'On', '132456', '2022-10-07 19:45:22', '132456', '2022-10-07 19:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2027', '12', 'Yes', '132456', '2022-10-07 19:45:22', '132456', '2022-10-07 19:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2027', '13', 'Yes', '132456', '2022-10-07 19:45:22', '132456', '2022-10-07 19:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2027', '14', 'Open', '132456', '2022-10-07 19:45:22', '132456', '2022-10-07 19:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2027', '16', '0.0', '132456', '2022-10-07 19:45:22', '132456', '2022-10-07 19:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2027', '26', 'On', '132456', '2022-10-07 19:45:22', '132456', '2022-10-07 19:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2027', '29', 'On', '132456', '2022-10-07 19:45:22', '132456', '2022-10-07 19:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2027', '42', 'true', '132456', '2022-10-07 19:45:22', '132456', '2022-10-07 19:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2027', '43', 'true', '132456', '2022-10-07 19:45:22', '132456', '2022-10-07 19:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20271', '2027', '1', '3', '2022-10-07 19:45:22', '2022-10-07 19:45:22', '2022-10-09 19:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-07 19:45:22', '132456', '2022-10-07 19:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20272', '2027', '2', '3', '2022-10-07 19:45:22', '2022-10-07 19:45:22', '2022-10-09 19:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-07 19:45:22', '132456', '2022-10-07 19:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20274', '2027', '4', '3', '2022-10-09 19:45:22', '2022-10-09 19:45:22', '2022-10-10 19:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-07 19:45:22', '132456', '2022-10-07 19:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20271', '2', '0', '132456', '2022-10-07 19:45:22', '132456', '2022-10-07 19:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20271', '5', 'No', '132456', '2022-10-07 19:45:22', '132456', '2022-10-07 19:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20272', '3', '0', '132456', '2022-10-07 19:45:22', '132456', '2022-10-07 19:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20272', '5', 'No', '132456', '2022-10-07 19:45:22', '132456', '2022-10-07 19:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20274', '5', 'No', '132456', '2022-10-07 19:45:22', '132456', '2022-10-07 19:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20274', '1', '30000413', '132456', '2022-10-07 19:45:22', '132456', '2022-10-07 19:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20271', '20272', '1', '1', '0', '132456', '2022-10-07 19:45:22', '132456', '2022-10-07 19:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20272', '20274', '0', '1', '0', '132456', '2022-10-07 19:45:22', '132456', '2022-10-07 19:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2027', '13', '2027', NULL, 132456, '132456', '2022-10-07 19:45:22', '132456', '2022-10-07 19:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2027', '1', '132456', '132456', '2022-10-07 19:45:22', '132456', '2022-10-07 19:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2027', '2', 'dok', '132456', '2022-10-07 19:45:22', '132456', '2022-10-07 19:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2028', '1', '17', '132456', '2022-10-10 08:45:22', '132456', '2022-10-10 08:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2028', '3', '1', '132456', '2022-10-10 08:45:22', '132456', '2022-10-10 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2028', '4', '0', '132456', '2022-10-10 08:45:22', '132456', '2022-10-10 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2028', '6', 'Design 2 Round Challenge #2028', '132456', '2022-10-10 08:45:22', '132456', '2022-10-10 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2028', '7', '1.0', '132456', '2022-10-10 08:45:22', '132456', '2022-10-10 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2028', '9', 'On', '132456', '2022-10-10 08:45:22', '132456', '2022-10-10 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2028', '10', 'On', '132456', '2022-10-10 08:45:22', '132456', '2022-10-10 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2028', '11', 'On', '132456', '2022-10-10 08:45:22', '132456', '2022-10-10 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2028', '12', 'Yes', '132456', '2022-10-10 08:45:22', '132456', '2022-10-10 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2028', '13', 'Yes', '132456', '2022-10-10 08:45:22', '132456', '2022-10-10 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2028', '14', 'Open', '132456', '2022-10-10 08:45:22', '132456', '2022-10-10 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2028', '16', '0.0', '132456', '2022-10-10 08:45:22', '132456', '2022-10-10 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2028', '26', 'On', '132456', '2022-10-10 08:45:22', '132456', '2022-10-10 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2028', '29', 'On', '132456', '2022-10-10 08:45:22', '132456', '2022-10-10 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2028', '42', 'true', '132456', '2022-10-10 08:45:22', '132456', '2022-10-10 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2028', '43', 'true', '132456', '2022-10-10 08:45:22', '132456', '2022-10-10 08:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('202813', '2028', '13', '3', '2022-10-10 08:45:22', '2022-10-10 08:45:22', '2022-10-10 08:45:22', NULL, NULL, '0.0', '132456', '2022-10-10 08:45:22', '132456', '2022-10-10 08:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('202814', '2028', '14', '3', '2022-10-10 08:45:22', '2022-10-10 08:45:22', '2022-10-10 09:45:22', NULL, NULL, '3600000.0', '132456', '2022-10-10 08:45:22', '132456', '2022-10-10 08:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20281', '2028', '1', '2', '2022-10-10 09:45:22', '2022-10-10 09:45:22', '2022-10-12 09:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-10 08:45:22', '132456', '2022-10-10 08:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('202815', '2028', '15', '2', '2022-10-10 09:45:22', '2022-10-10 09:45:22', '2022-10-12 09:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-10 08:45:22', '132456', '2022-10-10 08:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('202816', '2028', '16', '1', '2022-10-12 09:45:22', '2022-10-12 09:45:22', '2022-10-12 15:45:22', NULL, NULL, '21600000.0', '132456', '2022-10-10 08:45:22', '132456', '2022-10-10 08:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('202817', '2028', '17', '1', '2022-10-12 15:45:22', '2022-10-12 15:45:22', '2022-10-13 15:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-10 08:45:22', '132456', '2022-10-10 08:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20282', '2028', '2', '2', '2022-10-10 09:45:22', '2022-10-10 09:45:22', '2022-10-14 09:45:22', NULL, NULL, '345600000.0', '132456', '2022-10-10 08:45:22', '132456', '2022-10-10 08:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20283', '2028', '3', '1', '2022-10-14 09:45:22', '2022-10-14 09:45:22', '2022-10-14 21:45:22', NULL, NULL, '43200000.0', '132456', '2022-10-10 08:45:22', '132456', '2022-10-10 08:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20284', '2028', '4', '1', '2022-10-14 21:45:22', '2022-10-14 21:45:22', '2022-10-15 09:45:22', NULL, NULL, '43200000.0', '132456', '2022-10-10 08:45:22', '132456', '2022-10-10 08:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('202811', '2028', '11', '1', '2022-10-15 09:45:22', '2022-10-15 09:45:22', '2022-10-15 21:45:22', NULL, NULL, '43200000.0', '132456', '2022-10-10 08:45:22', '132456', '2022-10-10 08:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20289', '2028', '9', '1', '2022-10-15 21:45:22', '2022-10-15 21:45:22', '2022-10-16 21:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-10 08:45:22', '132456', '2022-10-10 08:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('202810', '2028', '10', '1', '2022-10-16 21:45:22', '2022-10-16 21:45:22', '2022-10-17 03:45:22', NULL, NULL, '21600000.0', '132456', '2022-10-10 08:45:22', '132456', '2022-10-10 08:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('202813', '3', '0', '132456', '2022-10-10 08:45:22', '132456', '2022-10-10 08:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('202813', '5', 'No', '132456', '2022-10-10 08:45:22', '132456', '2022-10-10 08:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('202814', '1', '30000722', '132456', '2022-10-10 08:45:22', '132456', '2022-10-10 08:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('202814', '6', '3', '132456', '2022-10-10 08:45:22', '132456', '2022-10-10 08:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20281', '2', '0', '132456', '2022-10-10 08:45:22', '132456', '2022-10-10 08:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20281', '5', 'No', '132456', '2022-10-10 08:45:22', '132456', '2022-10-10 08:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('202815', '3', '0', '132456', '2022-10-10 08:45:22', '132456', '2022-10-10 08:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('202815', '5', 'No', '132456', '2022-10-10 08:45:22', '132456', '2022-10-10 08:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('202816', '1', '30000416', '132456', '2022-10-10 08:45:22', '132456', '2022-10-10 08:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('202817', '1', '30000417', '132456', '2022-10-10 08:45:22', '132456', '2022-10-10 08:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('202817', '6', '3', '132456', '2022-10-10 08:45:22', '132456', '2022-10-10 08:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20282', '3', '0', '132456', '2022-10-10 08:45:22', '132456', '2022-10-10 08:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20282', '5', 'No', '132456', '2022-10-10 08:45:22', '132456', '2022-10-10 08:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20283', '1', '30000410', '132456', '2022-10-10 08:45:22', '132456', '2022-10-10 08:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20284', '5', 'No', '132456', '2022-10-10 08:45:22', '132456', '2022-10-10 08:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20284', '1', '30000411', '132456', '2022-10-10 08:45:22', '132456', '2022-10-10 08:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20284', '6', '3', '132456', '2022-10-10 08:45:22', '132456', '2022-10-10 08:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('202811', '1', '30000720', '132456', '2022-10-10 08:45:22', '132456', '2022-10-10 08:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20289', '5', 'No', '132456', '2022-10-10 08:45:22', '132456', '2022-10-10 08:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('202810', '5', 'No', '132456', '2022-10-10 08:45:22', '132456', '2022-10-10 08:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('202813', '202814', '1', '1', '0', '132456', '2022-10-10 08:45:22', '132456', '2022-10-10 08:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('202814', '20281', '0', '1', '0', '132456', '2022-10-10 08:45:22', '132456', '2022-10-10 08:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20281', '202815', '0', '1', '0', '132456', '2022-10-10 08:45:22', '132456', '2022-10-10 08:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('202815', '202816', '0', '1', '0', '132456', '2022-10-10 08:45:22', '132456', '2022-10-10 08:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('202816', '202817', '0', '1', '0', '132456', '2022-10-10 08:45:22', '132456', '2022-10-10 08:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('202817', '20282', '0', '1', '0', '132456', '2022-10-10 08:45:22', '132456', '2022-10-10 08:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20282', '20283', '0', '1', '0', '132456', '2022-10-10 08:45:22', '132456', '2022-10-10 08:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20283', '20284', '0', '1', '0', '132456', '2022-10-10 08:45:22', '132456', '2022-10-10 08:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20284', '202811', '0', '1', '0', '132456', '2022-10-10 08:45:22', '132456', '2022-10-10 08:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('202811', '20289', '0', '1', '0', '132456', '2022-10-10 08:45:22', '132456', '2022-10-10 08:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20289', '202810', '0', '1', '0', '132456', '2022-10-10 08:45:22', '132456', '2022-10-10 08:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2028', '13', '2028', NULL, 132456, '132456', '2022-10-10 08:45:22', '132456', '2022-10-10 08:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2028', '1', '132456', '132456', '2022-10-10 08:45:22', '132456', '2022-10-10 08:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2028', '2', 'dok', '132456', '2022-10-10 08:45:22', '132456', '2022-10-10 08:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2029', '1', '17', '132456', '2022-10-04 14:45:22', '132456', '2022-10-04 14:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2029', '3', '1', '132456', '2022-10-04 14:45:22', '132456', '2022-10-04 14:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2029', '4', '0', '132456', '2022-10-04 14:45:22', '132456', '2022-10-04 14:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2029', '6', 'Design 2 Round Challenge #2029', '132456', '2022-10-04 14:45:22', '132456', '2022-10-04 14:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2029', '7', '1.0', '132456', '2022-10-04 14:45:22', '132456', '2022-10-04 14:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2029', '9', 'On', '132456', '2022-10-04 14:45:22', '132456', '2022-10-04 14:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2029', '10', 'On', '132456', '2022-10-04 14:45:22', '132456', '2022-10-04 14:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2029', '11', 'On', '132456', '2022-10-04 14:45:22', '132456', '2022-10-04 14:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2029', '12', 'Yes', '132456', '2022-10-04 14:45:22', '132456', '2022-10-04 14:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2029', '13', 'Yes', '132456', '2022-10-04 14:45:22', '132456', '2022-10-04 14:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2029', '14', 'Open', '132456', '2022-10-04 14:45:22', '132456', '2022-10-04 14:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2029', '16', '0.0', '132456', '2022-10-04 14:45:22', '132456', '2022-10-04 14:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2029', '26', 'On', '132456', '2022-10-04 14:45:22', '132456', '2022-10-04 14:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2029', '29', 'On', '132456', '2022-10-04 14:45:22', '132456', '2022-10-04 14:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2029', '42', 'true', '132456', '2022-10-04 14:45:22', '132456', '2022-10-04 14:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2029', '43', 'true', '132456', '2022-10-04 14:45:22', '132456', '2022-10-04 14:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('202913', '2029', '13', '3', '2022-10-04 14:45:22', '2022-10-04 14:45:22', '2022-10-04 14:45:22', NULL, NULL, '0.0', '132456', '2022-10-04 14:45:22', '132456', '2022-10-04 14:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('202914', '2029', '14', '3', '2022-10-04 14:45:22', '2022-10-04 14:45:22', '2022-10-04 15:45:22', NULL, NULL, '3600000.0', '132456', '2022-10-04 14:45:22', '132456', '2022-10-04 14:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20291', '2029', '1', '3', '2022-10-04 15:45:22', '2022-10-04 15:45:22', '2022-10-06 15:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-04 14:45:22', '132456', '2022-10-04 14:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('202915', '2029', '15', '3', '2022-10-04 15:45:22', '2022-10-04 15:45:22', '2022-10-06 15:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-04 14:45:22', '132456', '2022-10-04 14:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('202916', '2029', '16', '3', '2022-10-06 15:45:22', '2022-10-06 15:45:22', '2022-10-06 21:45:22', NULL, NULL, '21600000.0', '132456', '2022-10-04 14:45:22', '132456', '2022-10-04 14:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('202917', '2029', '17', '3', '2022-10-06 21:45:22', '2022-10-06 21:45:22', '2022-10-07 21:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-04 14:45:22', '132456', '2022-10-04 14:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20292', '2029', '2', '3', '2022-10-04 15:45:22', '2022-10-04 15:45:22', '2022-10-08 15:45:22', NULL, NULL, '345600000.0', '132456', '2022-10-04 14:45:22', '132456', '2022-10-04 14:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20293', '2029', '3', '3', '2022-10-08 15:45:22', '2022-10-08 15:45:22', '2022-10-09 03:45:22', NULL, NULL, '43200000.0', '132456', '2022-10-04 14:45:22', '132456', '2022-10-04 14:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20294', '2029', '4', '3', '2022-10-09 03:45:22', '2022-10-09 03:45:22', '2022-10-09 15:45:22', NULL, NULL, '43200000.0', '132456', '2022-10-04 14:45:22', '132456', '2022-10-04 14:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('202911', '2029', '11', '3', '2022-10-09 15:45:22', '2022-10-09 15:45:22', '2022-10-10 03:45:22', NULL, NULL, '43200000.0', '132456', '2022-10-04 14:45:22', '132456', '2022-10-04 14:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20299', '2029', '9', '2', '2022-10-10 03:45:22', '2022-10-10 03:45:22', '2022-10-11 03:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-04 14:45:22', '132456', '2022-10-04 14:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('202910', '2029', '10', '1', '2022-10-11 03:45:22', '2022-10-11 03:45:22', '2022-10-11 09:45:22', NULL, NULL, '21600000.0', '132456', '2022-10-04 14:45:22', '132456', '2022-10-04 14:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('202913', '3', '0', '132456', '2022-10-04 14:45:22', '132456', '2022-10-04 14:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('202913', '5', 'No', '132456', '2022-10-04 14:45:22', '132456', '2022-10-04 14:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('202914', '1', '30000722', '132456', '2022-10-04 14:45:22', '132456', '2022-10-04 14:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('202914', '6', '3', '132456', '2022-10-04 14:45:22', '132456', '2022-10-04 14:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20291', '2', '0', '132456', '2022-10-04 14:45:22', '132456', '2022-10-04 14:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20291', '5', 'No', '132456', '2022-10-04 14:45:22', '132456', '2022-10-04 14:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('202915', '3', '0', '132456', '2022-10-04 14:45:22', '132456', '2022-10-04 14:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('202915', '5', 'No', '132456', '2022-10-04 14:45:22', '132456', '2022-10-04 14:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('202916', '1', '30000416', '132456', '2022-10-04 14:45:22', '132456', '2022-10-04 14:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('202917', '1', '30000417', '132456', '2022-10-04 14:45:22', '132456', '2022-10-04 14:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('202917', '6', '3', '132456', '2022-10-04 14:45:22', '132456', '2022-10-04 14:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20292', '3', '0', '132456', '2022-10-04 14:45:22', '132456', '2022-10-04 14:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20292', '5', 'No', '132456', '2022-10-04 14:45:22', '132456', '2022-10-04 14:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20293', '1', '30000410', '132456', '2022-10-04 14:45:22', '132456', '2022-10-04 14:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20294', '5', 'No', '132456', '2022-10-04 14:45:22', '132456', '2022-10-04 14:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20294', '1', '30000411', '132456', '2022-10-04 14:45:22', '132456', '2022-10-04 14:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20294', '6', '3', '132456', '2022-10-04 14:45:22', '132456', '2022-10-04 14:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('202911', '1', '30000720', '132456', '2022-10-04 14:45:22', '132456', '2022-10-04 14:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20299', '5', 'No', '132456', '2022-10-04 14:45:22', '132456', '2022-10-04 14:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('202910', '5', 'No', '132456', '2022-10-04 14:45:22', '132456', '2022-10-04 14:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('202913', '202914', '1', '1', '0', '132456', '2022-10-04 14:45:22', '132456', '2022-10-04 14:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('202914', '20291', '0', '1', '0', '132456', '2022-10-04 14:45:22', '132456', '2022-10-04 14:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20291', '202915', '0', '1', '0', '132456', '2022-10-04 14:45:22', '132456', '2022-10-04 14:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('202915', '202916', '0', '1', '0', '132456', '2022-10-04 14:45:22', '132456', '2022-10-04 14:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('202916', '202917', '0', '1', '0', '132456', '2022-10-04 14:45:22', '132456', '2022-10-04 14:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('202917', '20292', '0', '1', '0', '132456', '2022-10-04 14:45:22', '132456', '2022-10-04 14:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20292', '20293', '0', '1', '0', '132456', '2022-10-04 14:45:22', '132456', '2022-10-04 14:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20293', '20294', '0', '1', '0', '132456', '2022-10-04 14:45:22', '132456', '2022-10-04 14:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20294', '202911', '0', '1', '0', '132456', '2022-10-04 14:45:22', '132456', '2022-10-04 14:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('202911', '20299', '0', '1', '0', '132456', '2022-10-04 14:45:22', '132456', '2022-10-04 14:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20299', '202910', '0', '1', '0', '132456', '2022-10-04 14:45:22', '132456', '2022-10-04 14:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2029', '13', '2029', NULL, 132456, '132456', '2022-10-04 14:45:22', '132456', '2022-10-04 14:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2029', '1', '132456', '132456', '2022-10-04 14:45:22', '132456', '2022-10-04 14:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2029', '2', 'dok', '132456', '2022-10-04 14:45:22', '132456', '2022-10-04 14:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2030', '1', '17', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2030', '3', '1', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2030', '4', '0', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2030', '6', 'Design 2 Round Challenge #2030', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2030', '7', '1.0', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2030', '9', 'On', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2030', '10', 'On', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2030', '11', 'On', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2030', '12', 'Yes', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2030', '13', 'Yes', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2030', '14', 'Open', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2030', '16', '0.0', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2030', '26', 'On', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2030', '29', 'On', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2030', '42', 'true', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2030', '43', 'true', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('203013', '2030', '13', '3', '2022-10-07 10:45:22', '2022-10-07 10:45:22', '2022-10-07 10:45:22', NULL, NULL, '0.0', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('203014', '2030', '14', '3', '2022-10-07 10:45:22', '2022-10-07 10:45:22', '2022-10-07 11:45:22', NULL, NULL, '3600000.0', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20301', '2030', '1', '3', '2022-10-07 11:45:22', '2022-10-07 11:45:22', '2022-10-09 11:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('203015', '2030', '15', '3', '2022-10-07 11:45:22', '2022-10-07 11:45:22', '2022-10-09 11:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('203016', '2030', '16', '3', '2022-10-09 11:45:22', '2022-10-09 11:45:22', '2022-10-09 17:45:22', NULL, NULL, '21600000.0', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('203017', '2030', '17', '3', '2022-10-09 17:45:22', '2022-10-09 17:45:22', '2022-10-10 17:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20302', '2030', '2', '2', '2022-10-07 11:45:22', '2022-10-07 11:45:22', '2022-10-11 11:45:22', NULL, NULL, '345600000.0', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20303', '2030', '3', '1', '2022-10-11 11:45:22', '2022-10-11 11:45:22', '2022-10-11 23:45:22', NULL, NULL, '43200000.0', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20304', '2030', '4', '1', '2022-10-11 23:45:22', '2022-10-11 23:45:22', '2022-10-12 11:45:22', NULL, NULL, '43200000.0', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('203011', '2030', '11', '1', '2022-10-12 11:45:22', '2022-10-12 11:45:22', '2022-10-12 23:45:22', NULL, NULL, '43200000.0', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20309', '2030', '9', '1', '2022-10-12 23:45:22', '2022-10-12 23:45:22', '2022-10-13 23:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('203010', '2030', '10', '1', '2022-10-13 23:45:22', '2022-10-13 23:45:22', '2022-10-14 05:45:22', NULL, NULL, '21600000.0', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('203013', '3', '0', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('203013', '5', 'No', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('203014', '1', '30000722', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('203014', '6', '3', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20301', '2', '0', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20301', '5', 'No', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('203015', '3', '0', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('203015', '5', 'No', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('203016', '1', '30000416', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('203017', '1', '30000417', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('203017', '6', '3', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20302', '3', '0', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20302', '5', 'No', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20303', '1', '30000410', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20304', '5', 'No', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20304', '1', '30000411', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20304', '6', '3', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('203011', '1', '30000720', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20309', '5', 'No', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('203010', '5', 'No', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('203013', '203014', '1', '1', '0', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('203014', '20301', '0', '1', '0', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20301', '203015', '0', '1', '0', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('203015', '203016', '0', '1', '0', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('203016', '203017', '0', '1', '0', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('203017', '20302', '0', '1', '0', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20302', '20303', '0', '1', '0', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20303', '20304', '0', '1', '0', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20304', '203011', '0', '1', '0', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('203011', '20309', '0', '1', '0', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20309', '203010', '0', '1', '0', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2030', '13', '2030', NULL, 132456, '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2030', '1', '132456', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2030', '2', 'dok', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2031', '1', '40', '132456', '2022-10-10 02:45:22', '132456', '2022-10-10 02:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2031', '3', '1', '132456', '2022-10-10 02:45:22', '132456', '2022-10-10 02:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2031', '4', '0', '132456', '2022-10-10 02:45:22', '132456', '2022-10-10 02:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2031', '6', 'First2Finish Design Challenge #2031', '132456', '2022-10-10 02:45:22', '132456', '2022-10-10 02:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2031', '7', '1.0', '132456', '2022-10-10 02:45:22', '132456', '2022-10-10 02:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2031', '9', 'On', '132456', '2022-10-10 02:45:22', '132456', '2022-10-10 02:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2031', '10', 'On', '132456', '2022-10-10 02:45:22', '132456', '2022-10-10 02:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2031', '11', 'On', '132456', '2022-10-10 02:45:22', '132456', '2022-10-10 02:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2031', '12', 'Yes', '132456', '2022-10-10 02:45:22', '132456', '2022-10-10 02:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2031', '13', 'Yes', '132456', '2022-10-10 02:45:22', '132456', '2022-10-10 02:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2031', '14', 'Open', '132456', '2022-10-10 02:45:22', '132456', '2022-10-10 02:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2031', '16', '0.0', '132456', '2022-10-10 02:45:22', '132456', '2022-10-10 02:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2031', '26', 'On', '132456', '2022-10-10 02:45:22', '132456', '2022-10-10 02:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2031', '29', 'On', '132456', '2022-10-10 02:45:22', '132456', '2022-10-10 02:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2031', '42', 'true', '132456', '2022-10-10 02:45:22', '132456', '2022-10-10 02:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2031', '43', 'true', '132456', '2022-10-10 02:45:22', '132456', '2022-10-10 02:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20311', '2031', '1', '2', '2022-10-10 02:45:22', '2022-10-10 02:45:22', '2022-11-09 02:45:22', NULL, NULL, '2592000000.0', '132456', '2022-10-10 02:45:22', '132456', '2022-10-10 02:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20312', '2031', '2', '2', '2022-10-10 02:45:22', '2022-10-10 02:45:22', '2022-11-09 02:45:22', NULL, NULL, '2592000000.0', '132456', '2022-10-10 02:45:22', '132456', '2022-10-10 02:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('203118', '2031', '18', '1', '2022-10-11 02:45:22', '2022-10-11 02:45:22', '2022-10-12 02:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-10 02:45:22', '132456', '2022-10-10 02:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20311', '2', '0', '132456', '2022-10-10 02:45:22', '132456', '2022-10-10 02:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20311', '5', 'No', '132456', '2022-10-10 02:45:22', '132456', '2022-10-10 02:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20312', '3', '0', '132456', '2022-10-10 02:45:22', '132456', '2022-10-10 02:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20312', '5', 'No', '132456', '2022-10-10 02:45:22', '132456', '2022-10-10 02:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('203118', '1', '30000419', '132456', '2022-10-10 02:45:22', '132456', '2022-10-10 02:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20311', '20312', '1', '1', '0', '132456', '2022-10-10 02:45:22', '132456', '2022-10-10 02:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20312', '203118', '0', '1', '0', '132456', '2022-10-10 02:45:22', '132456', '2022-10-10 02:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2031', '13', '2031', NULL, 132456, '132456', '2022-10-10 02:45:22', '132456', '2022-10-10 02:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2031', '1', '132456', '132456', '2022-10-10 02:45:22', '132456', '2022-10-10 02:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2031', '2', 'dok', '132456', '2022-10-10 02:45:22', '132456', '2022-10-10 02:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2032', '1', '38', '132456', '2022-10-07 13:45:22', '132456', '2022-10-07 13:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2032', '3', '1', '132456', '2022-10-07 13:45:22', '132456', '2022-10-07 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2032', '4', '0', '132456', '2022-10-07 13:45:22', '132456', '2022-10-07 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2032', '6', 'First2Finish Challenge #2032', '132456', '2022-10-07 13:45:22', '132456', '2022-10-07 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2032', '7', '1.0', '132456', '2022-10-07 13:45:22', '132456', '2022-10-07 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2032', '9', 'On', '132456', '2022-10-07 13:45:22', '132456', '2022-10-07 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2032', '10', 'On', '132456', '2022-10-07 13:45:22', '132456', '2022-10-07 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2032', '11', 'On', '132456', '2022-10-07 13:45:22', '132456', '2022-10-07 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2032', '12', 'Yes', '132456', '2022-10-07 13:45:22', '132456', '2022-10-07 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2032', '13', 'Yes', '132456', '2022-10-07 13:45:22', '132456', '2022-10-07 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2032', '14', 'Open', '132456', '2022-10-07 13:45:22', '132456', '2022-10-07 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2032', '16', '0.0', '132456', '2022-10-07 13:45:22', '132456', '2022-10-07 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2032', '26', 'On', '132456', '2022-10-07 13:45:22', '132456', '2022-10-07 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2032', '29', 'On', '132456', '2022-10-07 13:45:22', '132456', '2022-10-07 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2032', '42', 'true', '132456', '2022-10-07 13:45:22', '132456', '2022-10-07 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2032', '43', 'true', '132456', '2022-10-07 13:45:22', '132456', '2022-10-07 13:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20321', '2032', '1', '2', '2022-10-07 13:45:22', '2022-10-07 13:45:22', '2022-11-06 13:45:22', NULL, NULL, '2592000000.0', '132456', '2022-10-07 13:45:22', '132456', '2022-10-07 13:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20322', '2032', '2', '2', '2022-10-07 13:45:22', '2022-10-07 13:45:22', '2022-11-06 13:45:22', NULL, NULL, '2592000000.0', '132456', '2022-10-07 13:45:22', '132456', '2022-10-07 13:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('203218', '2032', '18', '3', '2022-10-08 13:45:22', '2022-10-08 13:45:22', '2022-10-09 13:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-07 13:45:22', '132456', '2022-10-07 13:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20321', '2', '0', '132456', '2022-10-07 13:45:22', '132456', '2022-10-07 13:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20321', '5', 'No', '132456', '2022-10-07 13:45:22', '132456', '2022-10-07 13:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20322', '3', '0', '132456', '2022-10-07 13:45:22', '132456', '2022-10-07 13:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20322', '5', 'No', '132456', '2022-10-07 13:45:22', '132456', '2022-10-07 13:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('203218', '1', '30000419', '132456', '2022-10-07 13:45:22', '132456', '2022-10-07 13:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20321', '20322', '1', '1', '0', '132456', '2022-10-07 13:45:22', '132456', '2022-10-07 13:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20322', '203218', '0', '1', '0', '132456', '2022-10-07 13:45:22', '132456', '2022-10-07 13:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2032', '13', '2032', NULL, 132456, '132456', '2022-10-07 13:45:22', '132456', '2022-10-07 13:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2032', '1', '132456', '132456', '2022-10-07 13:45:22', '132456', '2022-10-07 13:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2032', '2', 'dok', '132456', '2022-10-07 13:45:22', '132456', '2022-10-07 13:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2033', '1', '37', '132456', '2022-10-06 12:45:22', '132456', '2022-10-06 12:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2033', '3', '1', '132456', '2022-10-06 12:45:22', '132456', '2022-10-06 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2033', '4', '0', '132456', '2022-10-06 12:45:22', '132456', '2022-10-06 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2033', '6', 'Data Science Challenge #2033', '132456', '2022-10-06 12:45:22', '132456', '2022-10-06 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2033', '7', '1.0', '132456', '2022-10-06 12:45:22', '132456', '2022-10-06 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2033', '9', 'On', '132456', '2022-10-06 12:45:22', '132456', '2022-10-06 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2033', '10', 'On', '132456', '2022-10-06 12:45:22', '132456', '2022-10-06 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2033', '11', 'On', '132456', '2022-10-06 12:45:22', '132456', '2022-10-06 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2033', '12', 'Yes', '132456', '2022-10-06 12:45:22', '132456', '2022-10-06 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2033', '13', 'Yes', '132456', '2022-10-06 12:45:22', '132456', '2022-10-06 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2033', '14', 'Open', '132456', '2022-10-06 12:45:22', '132456', '2022-10-06 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2033', '16', '0.0', '132456', '2022-10-06 12:45:22', '132456', '2022-10-06 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2033', '26', 'On', '132456', '2022-10-06 12:45:22', '132456', '2022-10-06 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2033', '29', 'On', '132456', '2022-10-06 12:45:22', '132456', '2022-10-06 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2033', '42', 'true', '132456', '2022-10-06 12:45:22', '132456', '2022-10-06 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2033', '43', 'true', '132456', '2022-10-06 12:45:22', '132456', '2022-10-06 12:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20331', '2033', '1', '3', '2022-10-06 12:45:22', '2022-10-06 12:45:22', '2022-10-08 12:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-06 12:45:22', '132456', '2022-10-06 12:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20332', '2033', '2', '3', '2022-10-06 12:45:22', '2022-10-06 12:45:22', '2022-10-08 12:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-06 12:45:22', '132456', '2022-10-06 12:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20334', '2033', '4', '3', '2022-10-08 12:45:22', '2022-10-08 12:45:22', '2022-10-09 12:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-06 12:45:22', '132456', '2022-10-06 12:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20331', '2', '0', '132456', '2022-10-06 12:45:22', '132456', '2022-10-06 12:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20331', '5', 'No', '132456', '2022-10-06 12:45:22', '132456', '2022-10-06 12:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20332', '3', '0', '132456', '2022-10-06 12:45:22', '132456', '2022-10-06 12:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20332', '5', 'No', '132456', '2022-10-06 12:45:22', '132456', '2022-10-06 12:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20334', '5', 'No', '132456', '2022-10-06 12:45:22', '132456', '2022-10-06 12:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20334', '1', '30000413', '132456', '2022-10-06 12:45:22', '132456', '2022-10-06 12:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20331', '20332', '1', '1', '0', '132456', '2022-10-06 12:45:22', '132456', '2022-10-06 12:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20332', '20334', '0', '1', '0', '132456', '2022-10-06 12:45:22', '132456', '2022-10-06 12:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2033', '13', '2033', NULL, 132456, '132456', '2022-10-06 12:45:22', '132456', '2022-10-06 12:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2033', '1', '132456', '132456', '2022-10-06 12:45:22', '132456', '2022-10-06 12:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2033', '2', 'dok', '132456', '2022-10-06 12:45:22', '132456', '2022-10-06 12:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2034', '1', '38', '132456', '2022-10-09 16:45:22', '132456', '2022-10-09 16:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2034', '3', '1', '132456', '2022-10-09 16:45:22', '132456', '2022-10-09 16:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2034', '4', '0', '132456', '2022-10-09 16:45:22', '132456', '2022-10-09 16:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2034', '6', 'First2Finish Challenge #2034', '132456', '2022-10-09 16:45:22', '132456', '2022-10-09 16:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2034', '7', '1.0', '132456', '2022-10-09 16:45:22', '132456', '2022-10-09 16:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2034', '9', 'On', '132456', '2022-10-09 16:45:22', '132456', '2022-10-09 16:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2034', '10', 'On', '132456', '2022-10-09 16:45:22', '132456', '2022-10-09 16:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2034', '11', 'On', '132456', '2022-10-09 16:45:22', '132456', '2022-10-09 16:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2034', '12', 'Yes', '132456', '2022-10-09 16:45:22', '132456', '2022-10-09 16:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2034', '13', 'Yes', '132456', '2022-10-09 16:45:22', '132456', '2022-10-09 16:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2034', '14', 'Open', '132456', '2022-10-09 16:45:22', '132456', '2022-10-09 16:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2034', '16', '0.0', '132456', '2022-10-09 16:45:22', '132456', '2022-10-09 16:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2034', '26', 'On', '132456', '2022-10-09 16:45:22', '132456', '2022-10-09 16:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2034', '29', 'On', '132456', '2022-10-09 16:45:22', '132456', '2022-10-09 16:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2034', '42', 'true', '132456', '2022-10-09 16:45:22', '132456', '2022-10-09 16:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2034', '43', 'true', '132456', '2022-10-09 16:45:22', '132456', '2022-10-09 16:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20341', '2034', '1', '2', '2022-10-09 16:45:22', '2022-10-09 16:45:22', '2022-11-08 16:45:22', NULL, NULL, '2592000000.0', '132456', '2022-10-09 16:45:22', '132456', '2022-10-09 16:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20342', '2034', '2', '2', '2022-10-09 16:45:22', '2022-10-09 16:45:22', '2022-11-08 16:45:22', NULL, NULL, '2592000000.0', '132456', '2022-10-09 16:45:22', '132456', '2022-10-09 16:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('203418', '2034', '18', '2', '2022-10-10 16:45:22', '2022-10-10 16:45:22', '2022-10-11 16:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-09 16:45:22', '132456', '2022-10-09 16:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20341', '2', '0', '132456', '2022-10-09 16:45:22', '132456', '2022-10-09 16:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20341', '5', 'No', '132456', '2022-10-09 16:45:22', '132456', '2022-10-09 16:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20342', '3', '0', '132456', '2022-10-09 16:45:22', '132456', '2022-10-09 16:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20342', '5', 'No', '132456', '2022-10-09 16:45:22', '132456', '2022-10-09 16:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('203418', '1', '30000419', '132456', '2022-10-09 16:45:22', '132456', '2022-10-09 16:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20341', '20342', '1', '1', '0', '132456', '2022-10-09 16:45:22', '132456', '2022-10-09 16:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20342', '203418', '0', '1', '0', '132456', '2022-10-09 16:45:22', '132456', '2022-10-09 16:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2034', '13', '2034', NULL, 132456, '132456', '2022-10-09 16:45:22', '132456', '2022-10-09 16:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2034', '1', '132456', '132456', '2022-10-09 16:45:22', '132456', '2022-10-09 16:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2034', '2', 'dok', '132456', '2022-10-09 16:45:22', '132456', '2022-10-09 16:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2035', '1', '9', '132456', '2022-10-10 13:45:22', '132456', '2022-10-10 13:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2035', '3', '1', '132456', '2022-10-10 13:45:22', '132456', '2022-10-10 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2035', '4', '0', '132456', '2022-10-10 13:45:22', '132456', '2022-10-10 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2035', '6', 'QA Challenge #2035', '132456', '2022-10-10 13:45:22', '132456', '2022-10-10 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2035', '7', '1.0', '132456', '2022-10-10 13:45:22', '132456', '2022-10-10 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2035', '9', 'On', '132456', '2022-10-10 13:45:22', '132456', '2022-10-10 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2035', '10', 'On', '132456', '2022-10-10 13:45:22', '132456', '2022-10-10 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2035', '11', 'On', '132456', '2022-10-10 13:45:22', '132456', '2022-10-10 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2035', '12', 'Yes', '132456', '2022-10-10 13:45:22', '132456', '2022-10-10 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2035', '13', 'Yes', '132456', '2022-10-10 13:45:22', '132456', '2022-10-10 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2035', '14', 'Open', '132456', '2022-10-10 13:45:22', '132456', '2022-10-10 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2035', '16', '0.0', '132456', '2022-10-10 13:45:22', '132456', '2022-10-10 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2035', '26', 'On', '132456', '2022-10-10 13:45:22', '132456', '2022-10-10 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2035', '29', 'On', '132456', '2022-10-10 13:45:22', '132456', '2022-10-10 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2035', '42', 'true', '132456', '2022-10-10 13:45:22', '132456', '2022-10-10 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2035', '43', 'true', '132456', '2022-10-10 13:45:22', '132456', '2022-10-10 13:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20351', '2035', '1', '2', '2022-10-10 13:45:22', '2022-10-10 13:45:22', '2022-10-12 13:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-10 13:45:22', '132456', '2022-10-10 13:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20352', '2035', '2', '2', '2022-10-10 13:45:22', '2022-10-10 13:45:22', '2022-10-12 13:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-10 13:45:22', '132456', '2022-10-10 13:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20354', '2035', '4', '1', '2022-10-12 13:45:22', '2022-10-12 13:45:22', '2022-10-13 13:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-10 13:45:22', '132456', '2022-10-10 13:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20351', '2', '0', '132456', '2022-10-10 13:45:22', '132456', '2022-10-10 13:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20351', '5', 'No', '132456', '2022-10-10 13:45:22', '132456', '2022-10-10 13:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20352', '3', '0', '132456', '2022-10-10 13:45:22', '132456', '2022-10-10 13:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20352', '5', 'No', '132456', '2022-10-10 13:45:22', '132456', '2022-10-10 13:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20354', '5', 'No', '132456', '2022-10-10 13:45:22', '132456', '2022-10-10 13:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20354', '1', '30000413', '132456', '2022-10-10 13:45:22', '132456', '2022-10-10 13:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20351', '20352', '1', '1', '0', '132456', '2022-10-10 13:45:22', '132456', '2022-10-10 13:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20352', '20354', '0', '1', '0', '132456', '2022-10-10 13:45:22', '132456', '2022-10-10 13:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2035', '13', '2035', NULL, 132456, '132456', '2022-10-10 13:45:22', '132456', '2022-10-10 13:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2035', '1', '132456', '132456', '2022-10-10 13:45:22', '132456', '2022-10-10 13:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2035', '2', 'dok', '132456', '2022-10-10 13:45:22', '132456', '2022-10-10 13:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2036', '1', '39', '132456', '2022-10-07 15:45:22', '132456', '2022-10-07 15:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2036', '3', '1', '132456', '2022-10-07 15:45:22', '132456', '2022-10-07 15:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2036', '4', '0', '132456', '2022-10-07 15:45:22', '132456', '2022-10-07 15:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2036', '6', 'Development Challenge #2036', '132456', '2022-10-07 15:45:22', '132456', '2022-10-07 15:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2036', '7', '1.0', '132456', '2022-10-07 15:45:22', '132456', '2022-10-07 15:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2036', '9', 'On', '132456', '2022-10-07 15:45:22', '132456', '2022-10-07 15:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2036', '10', 'On', '132456', '2022-10-07 15:45:22', '132456', '2022-10-07 15:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2036', '11', 'On', '132456', '2022-10-07 15:45:22', '132456', '2022-10-07 15:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2036', '12', 'Yes', '132456', '2022-10-07 15:45:22', '132456', '2022-10-07 15:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2036', '13', 'Yes', '132456', '2022-10-07 15:45:22', '132456', '2022-10-07 15:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2036', '14', 'Open', '132456', '2022-10-07 15:45:22', '132456', '2022-10-07 15:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2036', '16', '0.0', '132456', '2022-10-07 15:45:22', '132456', '2022-10-07 15:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2036', '26', 'On', '132456', '2022-10-07 15:45:22', '132456', '2022-10-07 15:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2036', '29', 'On', '132456', '2022-10-07 15:45:22', '132456', '2022-10-07 15:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2036', '42', 'true', '132456', '2022-10-07 15:45:22', '132456', '2022-10-07 15:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2036', '43', 'true', '132456', '2022-10-07 15:45:22', '132456', '2022-10-07 15:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20361', '2036', '1', '3', '2022-10-07 15:45:22', '2022-10-07 15:45:22', '2022-10-09 15:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-07 15:45:22', '132456', '2022-10-07 15:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20362', '2036', '2', '3', '2022-10-07 15:45:22', '2022-10-07 15:45:22', '2022-10-09 15:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-07 15:45:22', '132456', '2022-10-07 15:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20364', '2036', '4', '3', '2022-10-09 15:45:22', '2022-10-09 15:45:22', '2022-10-10 15:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-07 15:45:22', '132456', '2022-10-07 15:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20365', '2036', '5', '2', '2022-10-10 15:45:22', '2022-10-10 15:45:22', '2022-10-11 15:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-07 15:45:22', '132456', '2022-10-07 15:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20366', '2036', '6', '1', '2022-10-11 15:45:22', '2022-10-11 15:45:22', '2022-10-12 03:45:22', NULL, NULL, '43200000.0', '132456', '2022-10-07 15:45:22', '132456', '2022-10-07 15:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20361', '2', '0', '132456', '2022-10-07 15:45:22', '132456', '2022-10-07 15:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20361', '5', 'No', '132456', '2022-10-07 15:45:22', '132456', '2022-10-07 15:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20362', '3', '0', '132456', '2022-10-07 15:45:22', '132456', '2022-10-07 15:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20362', '5', 'No', '132456', '2022-10-07 15:45:22', '132456', '2022-10-07 15:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20364', '5', 'No', '132456', '2022-10-07 15:45:22', '132456', '2022-10-07 15:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20364', '1', '30000413', '132456', '2022-10-07 15:45:22', '132456', '2022-10-07 15:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20365', '5', 'No', '132456', '2022-10-07 15:45:22', '132456', '2022-10-07 15:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20365', '4', 'No', '132456', '2022-10-07 15:45:22', '132456', '2022-10-07 15:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20366', '5', 'No', '132456', '2022-10-07 15:45:22', '132456', '2022-10-07 15:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20361', '20362', '1', '1', '0', '132456', '2022-10-07 15:45:22', '132456', '2022-10-07 15:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20362', '20364', '0', '1', '0', '132456', '2022-10-07 15:45:22', '132456', '2022-10-07 15:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20364', '20365', '0', '1', '0', '132456', '2022-10-07 15:45:22', '132456', '2022-10-07 15:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20365', '20366', '0', '1', '0', '132456', '2022-10-07 15:45:22', '132456', '2022-10-07 15:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2036', '13', '2036', NULL, 132456, '132456', '2022-10-07 15:45:22', '132456', '2022-10-07 15:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2036', '1', '132456', '132456', '2022-10-07 15:45:22', '132456', '2022-10-07 15:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2036', '2', 'dok', '132456', '2022-10-07 15:45:22', '132456', '2022-10-07 15:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2037', '1', '37', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2037', '3', '1', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2037', '4', '0', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2037', '6', 'Data Science Challenge #2037', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2037', '7', '1.0', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2037', '9', 'On', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2037', '10', 'On', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2037', '11', 'On', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2037', '12', 'Yes', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2037', '13', 'Yes', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2037', '14', 'Open', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2037', '16', '0.0', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2037', '26', 'On', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2037', '29', 'On', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2037', '42', 'true', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2037', '43', 'true', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20371', '2037', '1', '3', '2022-10-07 08:45:22', '2022-10-07 08:45:22', '2022-10-09 08:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20372', '2037', '2', '3', '2022-10-07 08:45:22', '2022-10-07 08:45:22', '2022-10-09 08:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20374', '2037', '4', '3', '2022-10-09 08:45:22', '2022-10-09 08:45:22', '2022-10-10 08:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20371', '2', '0', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20371', '5', 'No', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20372', '3', '0', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20372', '5', 'No', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20374', '5', 'No', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20374', '1', '30000413', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20371', '20372', '1', '1', '0', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20372', '20374', '0', '1', '0', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2037', '13', '2037', NULL, 132456, '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2037', '1', '132456', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2037', '2', 'dok', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2038', '1', '40', '132456', '2022-10-10 01:45:22', '132456', '2022-10-10 01:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2038', '3', '1', '132456', '2022-10-10 01:45:22', '132456', '2022-10-10 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2038', '4', '0', '132456', '2022-10-10 01:45:22', '132456', '2022-10-10 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2038', '6', 'First2Finish Design Challenge #2038', '132456', '2022-10-10 01:45:22', '132456', '2022-10-10 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2038', '7', '1.0', '132456', '2022-10-10 01:45:22', '132456', '2022-10-10 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2038', '9', 'On', '132456', '2022-10-10 01:45:22', '132456', '2022-10-10 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2038', '10', 'On', '132456', '2022-10-10 01:45:22', '132456', '2022-10-10 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2038', '11', 'On', '132456', '2022-10-10 01:45:22', '132456', '2022-10-10 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2038', '12', 'Yes', '132456', '2022-10-10 01:45:22', '132456', '2022-10-10 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2038', '13', 'Yes', '132456', '2022-10-10 01:45:22', '132456', '2022-10-10 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2038', '14', 'Open', '132456', '2022-10-10 01:45:22', '132456', '2022-10-10 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2038', '16', '0.0', '132456', '2022-10-10 01:45:22', '132456', '2022-10-10 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2038', '26', 'On', '132456', '2022-10-10 01:45:22', '132456', '2022-10-10 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2038', '29', 'On', '132456', '2022-10-10 01:45:22', '132456', '2022-10-10 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2038', '42', 'true', '132456', '2022-10-10 01:45:22', '132456', '2022-10-10 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2038', '43', 'true', '132456', '2022-10-10 01:45:22', '132456', '2022-10-10 01:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20381', '2038', '1', '2', '2022-10-10 01:45:22', '2022-10-10 01:45:22', '2022-11-09 01:45:22', NULL, NULL, '2592000000.0', '132456', '2022-10-10 01:45:22', '132456', '2022-10-10 01:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20382', '2038', '2', '2', '2022-10-10 01:45:22', '2022-10-10 01:45:22', '2022-11-09 01:45:22', NULL, NULL, '2592000000.0', '132456', '2022-10-10 01:45:22', '132456', '2022-10-10 01:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('203818', '2038', '18', '2', '2022-10-11 01:45:22', '2022-10-11 01:45:22', '2022-10-12 01:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-10 01:45:22', '132456', '2022-10-10 01:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20381', '2', '0', '132456', '2022-10-10 01:45:22', '132456', '2022-10-10 01:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20381', '5', 'No', '132456', '2022-10-10 01:45:22', '132456', '2022-10-10 01:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20382', '3', '0', '132456', '2022-10-10 01:45:22', '132456', '2022-10-10 01:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20382', '5', 'No', '132456', '2022-10-10 01:45:22', '132456', '2022-10-10 01:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('203818', '1', '30000419', '132456', '2022-10-10 01:45:22', '132456', '2022-10-10 01:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20381', '20382', '1', '1', '0', '132456', '2022-10-10 01:45:22', '132456', '2022-10-10 01:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20382', '203818', '0', '1', '0', '132456', '2022-10-10 01:45:22', '132456', '2022-10-10 01:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2038', '13', '2038', NULL, 132456, '132456', '2022-10-10 01:45:22', '132456', '2022-10-10 01:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2038', '1', '132456', '132456', '2022-10-10 01:45:22', '132456', '2022-10-10 01:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2038', '2', 'dok', '132456', '2022-10-10 01:45:22', '132456', '2022-10-10 01:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2039', '1', '9', '132456', '2022-10-07 13:45:22', '132456', '2022-10-07 13:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2039', '3', '1', '132456', '2022-10-07 13:45:22', '132456', '2022-10-07 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2039', '4', '0', '132456', '2022-10-07 13:45:22', '132456', '2022-10-07 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2039', '6', 'QA Challenge #2039', '132456', '2022-10-07 13:45:22', '132456', '2022-10-07 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2039', '7', '1.0', '132456', '2022-10-07 13:45:22', '132456', '2022-10-07 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2039', '9', 'On', '132456', '2022-10-07 13:45:22', '132456', '2022-10-07 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2039', '10', 'On', '132456', '2022-10-07 13:45:22', '132456', '2022-10-07 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2039', '11', 'On', '132456', '2022-10-07 13:45:22', '132456', '2022-10-07 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2039', '12', 'Yes', '132456', '2022-10-07 13:45:22', '132456', '2022-10-07 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2039', '13', 'Yes', '132456', '2022-10-07 13:45:22', '132456', '2022-10-07 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2039', '14', 'Open', '132456', '2022-10-07 13:45:22', '132456', '2022-10-07 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2039', '16', '0.0', '132456', '2022-10-07 13:45:22', '132456', '2022-10-07 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2039', '26', 'On', '132456', '2022-10-07 13:45:22', '132456', '2022-10-07 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2039', '29', 'On', '132456', '2022-10-07 13:45:22', '132456', '2022-10-07 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2039', '42', 'true', '132456', '2022-10-07 13:45:22', '132456', '2022-10-07 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2039', '43', 'true', '132456', '2022-10-07 13:45:22', '132456', '2022-10-07 13:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20391', '2039', '1', '3', '2022-10-07 13:45:22', '2022-10-07 13:45:22', '2022-10-09 13:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-07 13:45:22', '132456', '2022-10-07 13:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20392', '2039', '2', '3', '2022-10-07 13:45:22', '2022-10-07 13:45:22', '2022-10-09 13:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-07 13:45:22', '132456', '2022-10-07 13:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20394', '2039', '4', '3', '2022-10-09 13:45:22', '2022-10-09 13:45:22', '2022-10-10 13:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-07 13:45:22', '132456', '2022-10-07 13:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20391', '2', '0', '132456', '2022-10-07 13:45:22', '132456', '2022-10-07 13:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20391', '5', 'No', '132456', '2022-10-07 13:45:22', '132456', '2022-10-07 13:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20392', '3', '0', '132456', '2022-10-07 13:45:22', '132456', '2022-10-07 13:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20392', '5', 'No', '132456', '2022-10-07 13:45:22', '132456', '2022-10-07 13:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20394', '5', 'No', '132456', '2022-10-07 13:45:22', '132456', '2022-10-07 13:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20394', '1', '30000413', '132456', '2022-10-07 13:45:22', '132456', '2022-10-07 13:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20391', '20392', '1', '1', '0', '132456', '2022-10-07 13:45:22', '132456', '2022-10-07 13:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20392', '20394', '0', '1', '0', '132456', '2022-10-07 13:45:22', '132456', '2022-10-07 13:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2039', '13', '2039', NULL, 132456, '132456', '2022-10-07 13:45:22', '132456', '2022-10-07 13:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2039', '1', '132456', '132456', '2022-10-07 13:45:22', '132456', '2022-10-07 13:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2039', '2', 'dok', '132456', '2022-10-07 13:45:22', '132456', '2022-10-07 13:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2040', '1', '17', '132456', '2022-10-08 04:45:22', '132456', '2022-10-08 04:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2040', '3', '1', '132456', '2022-10-08 04:45:22', '132456', '2022-10-08 04:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2040', '4', '0', '132456', '2022-10-08 04:45:22', '132456', '2022-10-08 04:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2040', '6', 'Design 2 Round Challenge #2040', '132456', '2022-10-08 04:45:22', '132456', '2022-10-08 04:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2040', '7', '1.0', '132456', '2022-10-08 04:45:22', '132456', '2022-10-08 04:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2040', '9', 'On', '132456', '2022-10-08 04:45:22', '132456', '2022-10-08 04:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2040', '10', 'On', '132456', '2022-10-08 04:45:22', '132456', '2022-10-08 04:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2040', '11', 'On', '132456', '2022-10-08 04:45:22', '132456', '2022-10-08 04:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2040', '12', 'Yes', '132456', '2022-10-08 04:45:22', '132456', '2022-10-08 04:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2040', '13', 'Yes', '132456', '2022-10-08 04:45:22', '132456', '2022-10-08 04:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2040', '14', 'Open', '132456', '2022-10-08 04:45:22', '132456', '2022-10-08 04:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2040', '16', '0.0', '132456', '2022-10-08 04:45:22', '132456', '2022-10-08 04:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2040', '26', 'On', '132456', '2022-10-08 04:45:22', '132456', '2022-10-08 04:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2040', '29', 'On', '132456', '2022-10-08 04:45:22', '132456', '2022-10-08 04:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2040', '42', 'true', '132456', '2022-10-08 04:45:22', '132456', '2022-10-08 04:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2040', '43', 'true', '132456', '2022-10-08 04:45:22', '132456', '2022-10-08 04:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('204013', '2040', '13', '3', '2022-10-08 04:45:22', '2022-10-08 04:45:22', '2022-10-08 04:45:22', NULL, NULL, '0.0', '132456', '2022-10-08 04:45:22', '132456', '2022-10-08 04:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('204014', '2040', '14', '3', '2022-10-08 04:45:22', '2022-10-08 04:45:22', '2022-10-08 05:45:22', NULL, NULL, '3600000.0', '132456', '2022-10-08 04:45:22', '132456', '2022-10-08 04:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20401', '2040', '1', '3', '2022-10-08 05:45:22', '2022-10-08 05:45:22', '2022-10-10 05:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-08 04:45:22', '132456', '2022-10-08 04:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('204015', '2040', '15', '3', '2022-10-08 05:45:22', '2022-10-08 05:45:22', '2022-10-10 05:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-08 04:45:22', '132456', '2022-10-08 04:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('204016', '2040', '16', '3', '2022-10-10 05:45:22', '2022-10-10 05:45:22', '2022-10-10 11:45:22', NULL, NULL, '21600000.0', '132456', '2022-10-08 04:45:22', '132456', '2022-10-08 04:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('204017', '2040', '17', '2', '2022-10-10 11:45:22', '2022-10-10 11:45:22', '2022-10-11 11:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-08 04:45:22', '132456', '2022-10-08 04:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20402', '2040', '2', '2', '2022-10-08 05:45:22', '2022-10-08 05:45:22', '2022-10-12 05:45:22', NULL, NULL, '345600000.0', '132456', '2022-10-08 04:45:22', '132456', '2022-10-08 04:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20403', '2040', '3', '1', '2022-10-12 05:45:22', '2022-10-12 05:45:22', '2022-10-12 17:45:22', NULL, NULL, '43200000.0', '132456', '2022-10-08 04:45:22', '132456', '2022-10-08 04:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20404', '2040', '4', '1', '2022-10-12 17:45:22', '2022-10-12 17:45:22', '2022-10-13 05:45:22', NULL, NULL, '43200000.0', '132456', '2022-10-08 04:45:22', '132456', '2022-10-08 04:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('204011', '2040', '11', '1', '2022-10-13 05:45:22', '2022-10-13 05:45:22', '2022-10-13 17:45:22', NULL, NULL, '43200000.0', '132456', '2022-10-08 04:45:22', '132456', '2022-10-08 04:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20409', '2040', '9', '1', '2022-10-13 17:45:22', '2022-10-13 17:45:22', '2022-10-14 17:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-08 04:45:22', '132456', '2022-10-08 04:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('204010', '2040', '10', '1', '2022-10-14 17:45:22', '2022-10-14 17:45:22', '2022-10-14 23:45:22', NULL, NULL, '21600000.0', '132456', '2022-10-08 04:45:22', '132456', '2022-10-08 04:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('204013', '3', '0', '132456', '2022-10-08 04:45:22', '132456', '2022-10-08 04:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('204013', '5', 'No', '132456', '2022-10-08 04:45:22', '132456', '2022-10-08 04:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('204014', '1', '30000722', '132456', '2022-10-08 04:45:22', '132456', '2022-10-08 04:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('204014', '6', '3', '132456', '2022-10-08 04:45:22', '132456', '2022-10-08 04:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20401', '2', '0', '132456', '2022-10-08 04:45:22', '132456', '2022-10-08 04:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20401', '5', 'No', '132456', '2022-10-08 04:45:22', '132456', '2022-10-08 04:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('204015', '3', '0', '132456', '2022-10-08 04:45:22', '132456', '2022-10-08 04:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('204015', '5', 'No', '132456', '2022-10-08 04:45:22', '132456', '2022-10-08 04:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('204016', '1', '30000416', '132456', '2022-10-08 04:45:22', '132456', '2022-10-08 04:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('204017', '1', '30000417', '132456', '2022-10-08 04:45:22', '132456', '2022-10-08 04:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('204017', '6', '3', '132456', '2022-10-08 04:45:22', '132456', '2022-10-08 04:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20402', '3', '0', '132456', '2022-10-08 04:45:22', '132456', '2022-10-08 04:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20402', '5', 'No', '132456', '2022-10-08 04:45:22', '132456', '2022-10-08 04:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20403', '1', '30000410', '132456', '2022-10-08 04:45:22', '132456', '2022-10-08 04:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20404', '5', 'No', '132456', '2022-10-08 04:45:22', '132456', '2022-10-08 04:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20404', '1', '30000411', '132456', '2022-10-08 04:45:22', '132456', '2022-10-08 04:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20404', '6', '3', '132456', '2022-10-08 04:45:22', '132456', '2022-10-08 04:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('204011', '1', '30000720', '132456', '2022-10-08 04:45:22', '132456', '2022-10-08 04:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20409', '5', 'No', '132456', '2022-10-08 04:45:22', '132456', '2022-10-08 04:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('204010', '5', 'No', '132456', '2022-10-08 04:45:22', '132456', '2022-10-08 04:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('204013', '204014', '1', '1', '0', '132456', '2022-10-08 04:45:22', '132456', '2022-10-08 04:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('204014', '20401', '0', '1', '0', '132456', '2022-10-08 04:45:22', '132456', '2022-10-08 04:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20401', '204015', '0', '1', '0', '132456', '2022-10-08 04:45:22', '132456', '2022-10-08 04:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('204015', '204016', '0', '1', '0', '132456', '2022-10-08 04:45:22', '132456', '2022-10-08 04:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('204016', '204017', '0', '1', '0', '132456', '2022-10-08 04:45:22', '132456', '2022-10-08 04:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('204017', '20402', '0', '1', '0', '132456', '2022-10-08 04:45:22', '132456', '2022-10-08 04:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20402', '20403', '0', '1', '0', '132456', '2022-10-08 04:45:22', '132456', '2022-10-08 04:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20403', '20404', '0', '1', '0', '132456', '2022-10-08 04:45:22', '132456', '2022-10-08 04:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20404', '204011', '0', '1', '0', '132456', '2022-10-08 04:45:22', '132456', '2022-10-08 04:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('204011', '20409', '0', '1', '0', '132456', '2022-10-08 04:45:22', '132456', '2022-10-08 04:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20409', '204010', '0', '1', '0', '132456', '2022-10-08 04:45:22', '132456', '2022-10-08 04:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2040', '13', '2040', NULL, 132456, '132456', '2022-10-08 04:45:22', '132456', '2022-10-08 04:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2040', '1', '132456', '132456', '2022-10-08 04:45:22', '132456', '2022-10-08 04:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2040', '2', 'dok', '132456', '2022-10-08 04:45:22', '132456', '2022-10-08 04:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2041', '1', '17', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2041', '3', '1', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2041', '4', '0', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2041', '6', 'Design Challenge #2041', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2041', '7', '1.0', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2041', '9', 'On', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2041', '10', 'On', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2041', '11', 'On', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2041', '12', 'Yes', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2041', '13', 'Yes', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2041', '14', 'Open', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2041', '16', '0.0', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2041', '26', 'On', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2041', '29', 'On', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2041', '42', 'true', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2041', '43', 'true', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20411', '2041', '1', '3', '2022-10-07 21:45:22', '2022-10-07 21:45:22', '2022-10-09 21:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20412', '2041', '2', '3', '2022-10-07 21:45:22', '2022-10-07 21:45:22', '2022-10-09 21:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20413', '2041', '3', '3', '2022-10-09 21:45:22', '2022-10-09 21:45:22', '2022-10-10 09:45:22', NULL, NULL, '43200000.0', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20414', '2041', '4', '2', '2022-10-10 09:45:22', '2022-10-10 09:45:22', '2022-10-11 09:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('204111', '2041', '11', '1', '2022-10-11 09:45:22', '2022-10-11 09:45:22', '2022-10-11 21:45:22', NULL, NULL, '43200000.0', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20411', '2', '0', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20411', '5', 'No', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20412', '3', '0', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20412', '5', 'No', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20413', '1', '30000410', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20414', '5', 'No', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20414', '1', '30000411', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('204111', '1', '30000720', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20411', '20412', '1', '1', '0', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20412', '20413', '0', '1', '0', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20413', '20414', '0', '1', '0', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20414', '204111', '0', '1', '0', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2041', '13', '2041', NULL, 132456, '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2041', '1', '132456', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2041', '2', 'dok', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2042', '1', '37', '132456', '2022-10-05 04:45:22', '132456', '2022-10-05 04:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2042', '3', '1', '132456', '2022-10-05 04:45:22', '132456', '2022-10-05 04:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2042', '4', '0', '132456', '2022-10-05 04:45:22', '132456', '2022-10-05 04:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2042', '6', 'Data Science Challenge #2042', '132456', '2022-10-05 04:45:22', '132456', '2022-10-05 04:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2042', '7', '1.0', '132456', '2022-10-05 04:45:22', '132456', '2022-10-05 04:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2042', '9', 'On', '132456', '2022-10-05 04:45:22', '132456', '2022-10-05 04:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2042', '10', 'On', '132456', '2022-10-05 04:45:22', '132456', '2022-10-05 04:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2042', '11', 'On', '132456', '2022-10-05 04:45:22', '132456', '2022-10-05 04:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2042', '12', 'Yes', '132456', '2022-10-05 04:45:22', '132456', '2022-10-05 04:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2042', '13', 'Yes', '132456', '2022-10-05 04:45:22', '132456', '2022-10-05 04:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2042', '14', 'Open', '132456', '2022-10-05 04:45:22', '132456', '2022-10-05 04:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2042', '16', '0.0', '132456', '2022-10-05 04:45:22', '132456', '2022-10-05 04:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2042', '26', 'On', '132456', '2022-10-05 04:45:22', '132456', '2022-10-05 04:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2042', '29', 'On', '132456', '2022-10-05 04:45:22', '132456', '2022-10-05 04:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2042', '42', 'true', '132456', '2022-10-05 04:45:22', '132456', '2022-10-05 04:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2042', '43', 'true', '132456', '2022-10-05 04:45:22', '132456', '2022-10-05 04:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20421', '2042', '1', '3', '2022-10-05 04:45:22', '2022-10-05 04:45:22', '2022-10-07 04:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-05 04:45:22', '132456', '2022-10-05 04:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20422', '2042', '2', '3', '2022-10-05 04:45:22', '2022-10-05 04:45:22', '2022-10-07 04:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-05 04:45:22', '132456', '2022-10-05 04:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20424', '2042', '4', '3', '2022-10-07 04:45:22', '2022-10-07 04:45:22', '2022-10-08 04:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-05 04:45:22', '132456', '2022-10-05 04:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20421', '2', '0', '132456', '2022-10-05 04:45:22', '132456', '2022-10-05 04:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20421', '5', 'No', '132456', '2022-10-05 04:45:22', '132456', '2022-10-05 04:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20422', '3', '0', '132456', '2022-10-05 04:45:22', '132456', '2022-10-05 04:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20422', '5', 'No', '132456', '2022-10-05 04:45:22', '132456', '2022-10-05 04:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20424', '5', 'No', '132456', '2022-10-05 04:45:22', '132456', '2022-10-05 04:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20424', '1', '30000413', '132456', '2022-10-05 04:45:22', '132456', '2022-10-05 04:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20421', '20422', '1', '1', '0', '132456', '2022-10-05 04:45:22', '132456', '2022-10-05 04:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20422', '20424', '0', '1', '0', '132456', '2022-10-05 04:45:22', '132456', '2022-10-05 04:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2042', '13', '2042', NULL, 132456, '132456', '2022-10-05 04:45:22', '132456', '2022-10-05 04:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2042', '1', '132456', '132456', '2022-10-05 04:45:22', '132456', '2022-10-05 04:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2042', '2', 'dok', '132456', '2022-10-05 04:45:22', '132456', '2022-10-05 04:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2043', '1', '9', '132456', '2022-10-04 19:45:22', '132456', '2022-10-04 19:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2043', '3', '1', '132456', '2022-10-04 19:45:22', '132456', '2022-10-04 19:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2043', '4', '0', '132456', '2022-10-04 19:45:22', '132456', '2022-10-04 19:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2043', '6', 'QA Challenge #2043', '132456', '2022-10-04 19:45:22', '132456', '2022-10-04 19:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2043', '7', '1.0', '132456', '2022-10-04 19:45:22', '132456', '2022-10-04 19:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2043', '9', 'On', '132456', '2022-10-04 19:45:22', '132456', '2022-10-04 19:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2043', '10', 'On', '132456', '2022-10-04 19:45:22', '132456', '2022-10-04 19:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2043', '11', 'On', '132456', '2022-10-04 19:45:22', '132456', '2022-10-04 19:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2043', '12', 'Yes', '132456', '2022-10-04 19:45:22', '132456', '2022-10-04 19:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2043', '13', 'Yes', '132456', '2022-10-04 19:45:22', '132456', '2022-10-04 19:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2043', '14', 'Open', '132456', '2022-10-04 19:45:22', '132456', '2022-10-04 19:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2043', '16', '0.0', '132456', '2022-10-04 19:45:22', '132456', '2022-10-04 19:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2043', '26', 'On', '132456', '2022-10-04 19:45:22', '132456', '2022-10-04 19:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2043', '29', 'On', '132456', '2022-10-04 19:45:22', '132456', '2022-10-04 19:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2043', '42', 'true', '132456', '2022-10-04 19:45:22', '132456', '2022-10-04 19:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2043', '43', 'true', '132456', '2022-10-04 19:45:22', '132456', '2022-10-04 19:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20431', '2043', '1', '3', '2022-10-04 19:45:22', '2022-10-04 19:45:22', '2022-10-06 19:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-04 19:45:22', '132456', '2022-10-04 19:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20432', '2043', '2', '3', '2022-10-04 19:45:22', '2022-10-04 19:45:22', '2022-10-06 19:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-04 19:45:22', '132456', '2022-10-04 19:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20434', '2043', '4', '3', '2022-10-06 19:45:22', '2022-10-06 19:45:22', '2022-10-07 19:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-04 19:45:22', '132456', '2022-10-04 19:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20431', '2', '0', '132456', '2022-10-04 19:45:22', '132456', '2022-10-04 19:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20431', '5', 'No', '132456', '2022-10-04 19:45:22', '132456', '2022-10-04 19:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20432', '3', '0', '132456', '2022-10-04 19:45:22', '132456', '2022-10-04 19:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20432', '5', 'No', '132456', '2022-10-04 19:45:22', '132456', '2022-10-04 19:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20434', '5', 'No', '132456', '2022-10-04 19:45:22', '132456', '2022-10-04 19:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20434', '1', '30000413', '132456', '2022-10-04 19:45:22', '132456', '2022-10-04 19:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20431', '20432', '1', '1', '0', '132456', '2022-10-04 19:45:22', '132456', '2022-10-04 19:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20432', '20434', '0', '1', '0', '132456', '2022-10-04 19:45:22', '132456', '2022-10-04 19:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2043', '13', '2043', NULL, 132456, '132456', '2022-10-04 19:45:22', '132456', '2022-10-04 19:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2043', '1', '132456', '132456', '2022-10-04 19:45:22', '132456', '2022-10-04 19:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2043', '2', 'dok', '132456', '2022-10-04 19:45:22', '132456', '2022-10-04 19:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2044', '1', '17', '132456', '2022-10-07 01:45:22', '132456', '2022-10-07 01:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2044', '3', '1', '132456', '2022-10-07 01:45:22', '132456', '2022-10-07 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2044', '4', '0', '132456', '2022-10-07 01:45:22', '132456', '2022-10-07 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2044', '6', 'Design Challenge #2044', '132456', '2022-10-07 01:45:22', '132456', '2022-10-07 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2044', '7', '1.0', '132456', '2022-10-07 01:45:22', '132456', '2022-10-07 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2044', '9', 'On', '132456', '2022-10-07 01:45:22', '132456', '2022-10-07 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2044', '10', 'On', '132456', '2022-10-07 01:45:22', '132456', '2022-10-07 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2044', '11', 'On', '132456', '2022-10-07 01:45:22', '132456', '2022-10-07 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2044', '12', 'Yes', '132456', '2022-10-07 01:45:22', '132456', '2022-10-07 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2044', '13', 'Yes', '132456', '2022-10-07 01:45:22', '132456', '2022-10-07 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2044', '14', 'Open', '132456', '2022-10-07 01:45:22', '132456', '2022-10-07 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2044', '16', '0.0', '132456', '2022-10-07 01:45:22', '132456', '2022-10-07 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2044', '26', 'On', '132456', '2022-10-07 01:45:22', '132456', '2022-10-07 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2044', '29', 'On', '132456', '2022-10-07 01:45:22', '132456', '2022-10-07 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2044', '42', 'true', '132456', '2022-10-07 01:45:22', '132456', '2022-10-07 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2044', '43', 'true', '132456', '2022-10-07 01:45:22', '132456', '2022-10-07 01:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20441', '2044', '1', '3', '2022-10-07 01:45:22', '2022-10-07 01:45:22', '2022-10-09 01:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-07 01:45:22', '132456', '2022-10-07 01:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20442', '2044', '2', '3', '2022-10-07 01:45:22', '2022-10-07 01:45:22', '2022-10-09 01:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-07 01:45:22', '132456', '2022-10-07 01:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20443', '2044', '3', '3', '2022-10-09 01:45:22', '2022-10-09 01:45:22', '2022-10-09 13:45:22', NULL, NULL, '43200000.0', '132456', '2022-10-07 01:45:22', '132456', '2022-10-07 01:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20444', '2044', '4', '3', '2022-10-09 13:45:22', '2022-10-09 13:45:22', '2022-10-10 13:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-07 01:45:22', '132456', '2022-10-07 01:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('204411', '2044', '11', '3', '2022-10-10 13:45:22', '2022-10-10 13:45:22', '2022-10-11 01:45:22', NULL, NULL, '43200000.0', '132456', '2022-10-07 01:45:22', '132456', '2022-10-07 01:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20441', '2', '0', '132456', '2022-10-07 01:45:22', '132456', '2022-10-07 01:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20441', '5', 'No', '132456', '2022-10-07 01:45:22', '132456', '2022-10-07 01:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20442', '3', '0', '132456', '2022-10-07 01:45:22', '132456', '2022-10-07 01:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20442', '5', 'No', '132456', '2022-10-07 01:45:22', '132456', '2022-10-07 01:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20443', '1', '30000410', '132456', '2022-10-07 01:45:22', '132456', '2022-10-07 01:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20444', '5', 'No', '132456', '2022-10-07 01:45:22', '132456', '2022-10-07 01:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20444', '1', '30000411', '132456', '2022-10-07 01:45:22', '132456', '2022-10-07 01:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('204411', '1', '30000720', '132456', '2022-10-07 01:45:22', '132456', '2022-10-07 01:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20441', '20442', '1', '1', '0', '132456', '2022-10-07 01:45:22', '132456', '2022-10-07 01:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20442', '20443', '0', '1', '0', '132456', '2022-10-07 01:45:22', '132456', '2022-10-07 01:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20443', '20444', '0', '1', '0', '132456', '2022-10-07 01:45:22', '132456', '2022-10-07 01:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20444', '204411', '0', '1', '0', '132456', '2022-10-07 01:45:22', '132456', '2022-10-07 01:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2044', '13', '2044', NULL, 132456, '132456', '2022-10-07 01:45:22', '132456', '2022-10-07 01:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2044', '1', '132456', '132456', '2022-10-07 01:45:22', '132456', '2022-10-07 01:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2044', '2', 'dok', '132456', '2022-10-07 01:45:22', '132456', '2022-10-07 01:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2045', '1', '39', '132456', '2022-10-08 21:45:22', '132456', '2022-10-08 21:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2045', '3', '1', '132456', '2022-10-08 21:45:22', '132456', '2022-10-08 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2045', '4', '0', '132456', '2022-10-08 21:45:22', '132456', '2022-10-08 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2045', '6', 'Development Challenge #2045', '132456', '2022-10-08 21:45:22', '132456', '2022-10-08 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2045', '7', '1.0', '132456', '2022-10-08 21:45:22', '132456', '2022-10-08 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2045', '9', 'On', '132456', '2022-10-08 21:45:22', '132456', '2022-10-08 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2045', '10', 'On', '132456', '2022-10-08 21:45:22', '132456', '2022-10-08 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2045', '11', 'On', '132456', '2022-10-08 21:45:22', '132456', '2022-10-08 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2045', '12', 'Yes', '132456', '2022-10-08 21:45:22', '132456', '2022-10-08 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2045', '13', 'Yes', '132456', '2022-10-08 21:45:22', '132456', '2022-10-08 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2045', '14', 'Open', '132456', '2022-10-08 21:45:22', '132456', '2022-10-08 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2045', '16', '0.0', '132456', '2022-10-08 21:45:22', '132456', '2022-10-08 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2045', '26', 'On', '132456', '2022-10-08 21:45:22', '132456', '2022-10-08 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2045', '29', 'On', '132456', '2022-10-08 21:45:22', '132456', '2022-10-08 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2045', '42', 'true', '132456', '2022-10-08 21:45:22', '132456', '2022-10-08 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2045', '43', 'true', '132456', '2022-10-08 21:45:22', '132456', '2022-10-08 21:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20451', '2045', '1', '3', '2022-10-08 21:45:22', '2022-10-08 21:45:22', '2022-10-10 21:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-08 21:45:22', '132456', '2022-10-08 21:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20452', '2045', '2', '3', '2022-10-08 21:45:22', '2022-10-08 21:45:22', '2022-10-10 21:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-08 21:45:22', '132456', '2022-10-08 21:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20454', '2045', '4', '2', '2022-10-10 21:45:22', '2022-10-10 21:45:22', '2022-10-11 21:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-08 21:45:22', '132456', '2022-10-08 21:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20455', '2045', '5', '1', '2022-10-11 21:45:22', '2022-10-11 21:45:22', '2022-10-12 21:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-08 21:45:22', '132456', '2022-10-08 21:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20456', '2045', '6', '1', '2022-10-12 21:45:22', '2022-10-12 21:45:22', '2022-10-13 09:45:22', NULL, NULL, '43200000.0', '132456', '2022-10-08 21:45:22', '132456', '2022-10-08 21:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20451', '2', '0', '132456', '2022-10-08 21:45:22', '132456', '2022-10-08 21:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20451', '5', 'No', '132456', '2022-10-08 21:45:22', '132456', '2022-10-08 21:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20452', '3', '0', '132456', '2022-10-08 21:45:22', '132456', '2022-10-08 21:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20452', '5', 'No', '132456', '2022-10-08 21:45:22', '132456', '2022-10-08 21:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20454', '5', 'No', '132456', '2022-10-08 21:45:22', '132456', '2022-10-08 21:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20454', '1', '30000413', '132456', '2022-10-08 21:45:22', '132456', '2022-10-08 21:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20455', '5', 'No', '132456', '2022-10-08 21:45:22', '132456', '2022-10-08 21:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20455', '4', 'No', '132456', '2022-10-08 21:45:22', '132456', '2022-10-08 21:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20456', '5', 'No', '132456', '2022-10-08 21:45:22', '132456', '2022-10-08 21:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20451', '20452', '1', '1', '0', '132456', '2022-10-08 21:45:22', '132456', '2022-10-08 21:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20452', '20454', '0', '1', '0', '132456', '2022-10-08 21:45:22', '132456', '2022-10-08 21:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20454', '20455', '0', '1', '0', '132456', '2022-10-08 21:45:22', '132456', '2022-10-08 21:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20455', '20456', '0', '1', '0', '132456', '2022-10-08 21:45:22', '132456', '2022-10-08 21:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2045', '13', '2045', NULL, 132456, '132456', '2022-10-08 21:45:22', '132456', '2022-10-08 21:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2045', '1', '132456', '132456', '2022-10-08 21:45:22', '132456', '2022-10-08 21:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2045', '2', 'dok', '132456', '2022-10-08 21:45:22', '132456', '2022-10-08 21:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2046', '1', '37', '132456', '2022-10-05 02:45:22', '132456', '2022-10-05 02:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2046', '3', '1', '132456', '2022-10-05 02:45:22', '132456', '2022-10-05 02:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2046', '4', '0', '132456', '2022-10-05 02:45:22', '132456', '2022-10-05 02:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2046', '6', 'Data Science Challenge #2046', '132456', '2022-10-05 02:45:22', '132456', '2022-10-05 02:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2046', '7', '1.0', '132456', '2022-10-05 02:45:22', '132456', '2022-10-05 02:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2046', '9', 'On', '132456', '2022-10-05 02:45:22', '132456', '2022-10-05 02:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2046', '10', 'On', '132456', '2022-10-05 02:45:22', '132456', '2022-10-05 02:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2046', '11', 'On', '132456', '2022-10-05 02:45:22', '132456', '2022-10-05 02:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2046', '12', 'Yes', '132456', '2022-10-05 02:45:22', '132456', '2022-10-05 02:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2046', '13', 'Yes', '132456', '2022-10-05 02:45:22', '132456', '2022-10-05 02:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2046', '14', 'Open', '132456', '2022-10-05 02:45:22', '132456', '2022-10-05 02:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2046', '16', '0.0', '132456', '2022-10-05 02:45:22', '132456', '2022-10-05 02:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2046', '26', 'On', '132456', '2022-10-05 02:45:22', '132456', '2022-10-05 02:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2046', '29', 'On', '132456', '2022-10-05 02:45:22', '132456', '2022-10-05 02:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2046', '42', 'true', '132456', '2022-10-05 02:45:22', '132456', '2022-10-05 02:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2046', '43', 'true', '132456', '2022-10-05 02:45:22', '132456', '2022-10-05 02:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20461', '2046', '1', '3', '2022-10-05 02:45:22', '2022-10-05 02:45:22', '2022-10-07 02:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-05 02:45:22', '132456', '2022-10-05 02:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20462', '2046', '2', '3', '2022-10-05 02:45:22', '2022-10-05 02:45:22', '2022-10-07 02:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-05 02:45:22', '132456', '2022-10-05 02:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20464', '2046', '4', '3', '2022-10-07 02:45:22', '2022-10-07 02:45:22', '2022-10-08 02:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-05 02:45:22', '132456', '2022-10-05 02:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20461', '2', '0', '132456', '2022-10-05 02:45:22', '132456', '2022-10-05 02:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20461', '5', 'No', '132456', '2022-10-05 02:45:22', '132456', '2022-10-05 02:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20462', '3', '0', '132456', '2022-10-05 02:45:22', '132456', '2022-10-05 02:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20462', '5', 'No', '132456', '2022-10-05 02:45:22', '132456', '2022-10-05 02:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20464', '5', 'No', '132456', '2022-10-05 02:45:22', '132456', '2022-10-05 02:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20464', '1', '30000413', '132456', '2022-10-05 02:45:22', '132456', '2022-10-05 02:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20461', '20462', '1', '1', '0', '132456', '2022-10-05 02:45:22', '132456', '2022-10-05 02:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20462', '20464', '0', '1', '0', '132456', '2022-10-05 02:45:22', '132456', '2022-10-05 02:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2046', '13', '2046', NULL, 132456, '132456', '2022-10-05 02:45:22', '132456', '2022-10-05 02:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2046', '1', '132456', '132456', '2022-10-05 02:45:22', '132456', '2022-10-05 02:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2046', '2', 'dok', '132456', '2022-10-05 02:45:22', '132456', '2022-10-05 02:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2047', '1', '17', '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2047', '3', '1', '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2047', '4', '0', '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2047', '6', 'Design Challenge #2047', '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2047', '7', '1.0', '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2047', '9', 'On', '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2047', '10', 'On', '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2047', '11', 'On', '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2047', '12', 'Yes', '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2047', '13', 'Yes', '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2047', '14', 'Open', '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2047', '16', '0.0', '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2047', '26', 'On', '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2047', '29', 'On', '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2047', '42', 'true', '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2047', '43', 'true', '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20471', '2047', '1', '2', '2022-10-09 21:45:22', '2022-10-09 21:45:22', '2022-10-11 21:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20472', '2047', '2', '2', '2022-10-09 21:45:22', '2022-10-09 21:45:22', '2022-10-11 21:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20473', '2047', '3', '1', '2022-10-11 21:45:22', '2022-10-11 21:45:22', '2022-10-12 09:45:22', NULL, NULL, '43200000.0', '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20474', '2047', '4', '1', '2022-10-12 09:45:22', '2022-10-12 09:45:22', '2022-10-13 09:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('204711', '2047', '11', '1', '2022-10-13 09:45:22', '2022-10-13 09:45:22', '2022-10-13 21:45:22', NULL, NULL, '43200000.0', '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20471', '2', '0', '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20471', '5', 'No', '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20472', '3', '0', '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20472', '5', 'No', '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20473', '1', '30000410', '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20474', '5', 'No', '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20474', '1', '30000411', '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('204711', '1', '30000720', '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20471', '20472', '1', '1', '0', '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20472', '20473', '0', '1', '0', '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20473', '20474', '0', '1', '0', '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20474', '204711', '0', '1', '0', '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2047', '13', '2047', NULL, 132456, '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2047', '1', '132456', '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2047', '2', 'dok', '132456', '2022-10-09 21:45:22', '132456', '2022-10-09 21:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2048', '1', '38', '132456', '2022-10-06 03:45:22', '132456', '2022-10-06 03:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2048', '3', '1', '132456', '2022-10-06 03:45:22', '132456', '2022-10-06 03:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2048', '4', '0', '132456', '2022-10-06 03:45:22', '132456', '2022-10-06 03:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2048', '6', 'First2Finish Challenge #2048', '132456', '2022-10-06 03:45:22', '132456', '2022-10-06 03:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2048', '7', '1.0', '132456', '2022-10-06 03:45:22', '132456', '2022-10-06 03:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2048', '9', 'On', '132456', '2022-10-06 03:45:22', '132456', '2022-10-06 03:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2048', '10', 'On', '132456', '2022-10-06 03:45:22', '132456', '2022-10-06 03:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2048', '11', 'On', '132456', '2022-10-06 03:45:22', '132456', '2022-10-06 03:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2048', '12', 'Yes', '132456', '2022-10-06 03:45:22', '132456', '2022-10-06 03:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2048', '13', 'Yes', '132456', '2022-10-06 03:45:22', '132456', '2022-10-06 03:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2048', '14', 'Open', '132456', '2022-10-06 03:45:22', '132456', '2022-10-06 03:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2048', '16', '0.0', '132456', '2022-10-06 03:45:22', '132456', '2022-10-06 03:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2048', '26', 'On', '132456', '2022-10-06 03:45:22', '132456', '2022-10-06 03:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2048', '29', 'On', '132456', '2022-10-06 03:45:22', '132456', '2022-10-06 03:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2048', '42', 'true', '132456', '2022-10-06 03:45:22', '132456', '2022-10-06 03:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2048', '43', 'true', '132456', '2022-10-06 03:45:22', '132456', '2022-10-06 03:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20481', '2048', '1', '2', '2022-10-06 03:45:22', '2022-10-06 03:45:22', '2022-11-05 03:45:22', NULL, NULL, '2592000000.0', '132456', '2022-10-06 03:45:22', '132456', '2022-10-06 03:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20482', '2048', '2', '2', '2022-10-06 03:45:22', '2022-10-06 03:45:22', '2022-11-05 03:45:22', NULL, NULL, '2592000000.0', '132456', '2022-10-06 03:45:22', '132456', '2022-10-06 03:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('204818', '2048', '18', '3', '2022-10-07 03:45:22', '2022-10-07 03:45:22', '2022-10-08 03:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-06 03:45:22', '132456', '2022-10-06 03:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20481', '2', '0', '132456', '2022-10-06 03:45:22', '132456', '2022-10-06 03:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20481', '5', 'No', '132456', '2022-10-06 03:45:22', '132456', '2022-10-06 03:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20482', '3', '0', '132456', '2022-10-06 03:45:22', '132456', '2022-10-06 03:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20482', '5', 'No', '132456', '2022-10-06 03:45:22', '132456', '2022-10-06 03:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('204818', '1', '30000419', '132456', '2022-10-06 03:45:22', '132456', '2022-10-06 03:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20481', '20482', '1', '1', '0', '132456', '2022-10-06 03:45:22', '132456', '2022-10-06 03:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20482', '204818', '0', '1', '0', '132456', '2022-10-06 03:45:22', '132456', '2022-10-06 03:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2048', '13', '2048', NULL, 132456, '132456', '2022-10-06 03:45:22', '132456', '2022-10-06 03:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2048', '1', '132456', '132456', '2022-10-06 03:45:22', '132456', '2022-10-06 03:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2048', '2', 'dok', '132456', '2022-10-06 03:45:22', '132456', '2022-10-06 03:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2049', '1', '17', '132456', '2022-10-05 06:45:22', '132456', '2022-10-05 06:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2049', '3', '1', '132456', '2022-10-05 06:45:22', '132456', '2022-10-05 06:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2049', '4', '0', '132456', '2022-10-05 06:45:22', '132456', '2022-10-05 06:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2049', '6', 'Design Challenge #2049', '132456', '2022-10-05 06:45:22', '132456', '2022-10-05 06:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2049', '7', '1.0', '132456', '2022-10-05 06:45:22', '132456', '2022-10-05 06:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2049', '9', 'On', '132456', '2022-10-05 06:45:22', '132456', '2022-10-05 06:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2049', '10', 'On', '132456', '2022-10-05 06:45:22', '132456', '2022-10-05 06:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2049', '11', 'On', '132456', '2022-10-05 06:45:22', '132456', '2022-10-05 06:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2049', '12', 'Yes', '132456', '2022-10-05 06:45:22', '132456', '2022-10-05 06:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2049', '13', 'Yes', '132456', '2022-10-05 06:45:22', '132456', '2022-10-05 06:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2049', '14', 'Open', '132456', '2022-10-05 06:45:22', '132456', '2022-10-05 06:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2049', '16', '0.0', '132456', '2022-10-05 06:45:22', '132456', '2022-10-05 06:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2049', '26', 'On', '132456', '2022-10-05 06:45:22', '132456', '2022-10-05 06:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2049', '29', 'On', '132456', '2022-10-05 06:45:22', '132456', '2022-10-05 06:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2049', '42', 'true', '132456', '2022-10-05 06:45:22', '132456', '2022-10-05 06:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2049', '43', 'true', '132456', '2022-10-05 06:45:22', '132456', '2022-10-05 06:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20491', '2049', '1', '3', '2022-10-05 06:45:22', '2022-10-05 06:45:22', '2022-10-07 06:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-05 06:45:22', '132456', '2022-10-05 06:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20492', '2049', '2', '3', '2022-10-05 06:45:22', '2022-10-05 06:45:22', '2022-10-07 06:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-05 06:45:22', '132456', '2022-10-05 06:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20493', '2049', '3', '3', '2022-10-07 06:45:22', '2022-10-07 06:45:22', '2022-10-07 18:45:22', NULL, NULL, '43200000.0', '132456', '2022-10-05 06:45:22', '132456', '2022-10-05 06:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20494', '2049', '4', '3', '2022-10-07 18:45:22', '2022-10-07 18:45:22', '2022-10-08 18:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-05 06:45:22', '132456', '2022-10-05 06:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('204911', '2049', '11', '3', '2022-10-08 18:45:22', '2022-10-08 18:45:22', '2022-10-09 06:45:22', NULL, NULL, '43200000.0', '132456', '2022-10-05 06:45:22', '132456', '2022-10-05 06:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20491', '2', '0', '132456', '2022-10-05 06:45:22', '132456', '2022-10-05 06:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20491', '5', 'No', '132456', '2022-10-05 06:45:22', '132456', '2022-10-05 06:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20492', '3', '0', '132456', '2022-10-05 06:45:22', '132456', '2022-10-05 06:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20492', '5', 'No', '132456', '2022-10-05 06:45:22', '132456', '2022-10-05 06:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20493', '1', '30000410', '132456', '2022-10-05 06:45:22', '132456', '2022-10-05 06:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20494', '5', 'No', '132456', '2022-10-05 06:45:22', '132456', '2022-10-05 06:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20494', '1', '30000411', '132456', '2022-10-05 06:45:22', '132456', '2022-10-05 06:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('204911', '1', '30000720', '132456', '2022-10-05 06:45:22', '132456', '2022-10-05 06:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20491', '20492', '1', '1', '0', '132456', '2022-10-05 06:45:22', '132456', '2022-10-05 06:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20492', '20493', '0', '1', '0', '132456', '2022-10-05 06:45:22', '132456', '2022-10-05 06:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20493', '20494', '0', '1', '0', '132456', '2022-10-05 06:45:22', '132456', '2022-10-05 06:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20494', '204911', '0', '1', '0', '132456', '2022-10-05 06:45:22', '132456', '2022-10-05 06:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2049', '13', '2049', NULL, 132456, '132456', '2022-10-05 06:45:22', '132456', '2022-10-05 06:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2049', '1', '132456', '132456', '2022-10-05 06:45:22', '132456', '2022-10-05 06:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2049', '2', 'dok', '132456', '2022-10-05 06:45:22', '132456', '2022-10-05 06:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2050', '1', '9', '132456', '2022-10-10 04:45:22', '132456', '2022-10-10 04:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2050', '3', '1', '132456', '2022-10-10 04:45:22', '132456', '2022-10-10 04:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2050', '4', '0', '132456', '2022-10-10 04:45:22', '132456', '2022-10-10 04:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2050', '6', 'QA Challenge #2050', '132456', '2022-10-10 04:45:22', '132456', '2022-10-10 04:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2050', '7', '1.0', '132456', '2022-10-10 04:45:22', '132456', '2022-10-10 04:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2050', '9', 'On', '132456', '2022-10-10 04:45:22', '132456', '2022-10-10 04:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2050', '10', 'On', '132456', '2022-10-10 04:45:22', '132456', '2022-10-10 04:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2050', '11', 'On', '132456', '2022-10-10 04:45:22', '132456', '2022-10-10 04:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2050', '12', 'Yes', '132456', '2022-10-10 04:45:22', '132456', '2022-10-10 04:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2050', '13', 'Yes', '132456', '2022-10-10 04:45:22', '132456', '2022-10-10 04:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2050', '14', 'Open', '132456', '2022-10-10 04:45:22', '132456', '2022-10-10 04:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2050', '16', '0.0', '132456', '2022-10-10 04:45:22', '132456', '2022-10-10 04:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2050', '26', 'On', '132456', '2022-10-10 04:45:22', '132456', '2022-10-10 04:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2050', '29', 'On', '132456', '2022-10-10 04:45:22', '132456', '2022-10-10 04:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2050', '42', 'true', '132456', '2022-10-10 04:45:22', '132456', '2022-10-10 04:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2050', '43', 'true', '132456', '2022-10-10 04:45:22', '132456', '2022-10-10 04:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20501', '2050', '1', '2', '2022-10-10 04:45:22', '2022-10-10 04:45:22', '2022-10-12 04:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-10 04:45:22', '132456', '2022-10-10 04:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20502', '2050', '2', '2', '2022-10-10 04:45:22', '2022-10-10 04:45:22', '2022-10-12 04:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-10 04:45:22', '132456', '2022-10-10 04:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20504', '2050', '4', '1', '2022-10-12 04:45:22', '2022-10-12 04:45:22', '2022-10-13 04:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-10 04:45:22', '132456', '2022-10-10 04:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20501', '2', '0', '132456', '2022-10-10 04:45:22', '132456', '2022-10-10 04:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20501', '5', 'No', '132456', '2022-10-10 04:45:22', '132456', '2022-10-10 04:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20502', '3', '0', '132456', '2022-10-10 04:45:22', '132456', '2022-10-10 04:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20502', '5', 'No', '132456', '2022-10-10 04:45:22', '132456', '2022-10-10 04:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20504', '5', 'No', '132456', '2022-10-10 04:45:22', '132456', '2022-10-10 04:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20504', '1', '30000413', '132456', '2022-10-10 04:45:22', '132456', '2022-10-10 04:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20501', '20502', '1', '1', '0', '132456', '2022-10-10 04:45:22', '132456', '2022-10-10 04:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20502', '20504', '0', '1', '0', '132456', '2022-10-10 04:45:22', '132456', '2022-10-10 04:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2050', '13', '2050', NULL, 132456, '132456', '2022-10-10 04:45:22', '132456', '2022-10-10 04:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2050', '1', '132456', '132456', '2022-10-10 04:45:22', '132456', '2022-10-10 04:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2050', '2', 'dok', '132456', '2022-10-10 04:45:22', '132456', '2022-10-10 04:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2051', '1', '38', '132456', '2022-10-07 17:45:22', '132456', '2022-10-07 17:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2051', '3', '1', '132456', '2022-10-07 17:45:22', '132456', '2022-10-07 17:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2051', '4', '0', '132456', '2022-10-07 17:45:22', '132456', '2022-10-07 17:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2051', '6', 'First2Finish Challenge #2051', '132456', '2022-10-07 17:45:22', '132456', '2022-10-07 17:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2051', '7', '1.0', '132456', '2022-10-07 17:45:22', '132456', '2022-10-07 17:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2051', '9', 'On', '132456', '2022-10-07 17:45:22', '132456', '2022-10-07 17:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2051', '10', 'On', '132456', '2022-10-07 17:45:22', '132456', '2022-10-07 17:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2051', '11', 'On', '132456', '2022-10-07 17:45:22', '132456', '2022-10-07 17:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2051', '12', 'Yes', '132456', '2022-10-07 17:45:22', '132456', '2022-10-07 17:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2051', '13', 'Yes', '132456', '2022-10-07 17:45:22', '132456', '2022-10-07 17:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2051', '14', 'Open', '132456', '2022-10-07 17:45:22', '132456', '2022-10-07 17:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2051', '16', '0.0', '132456', '2022-10-07 17:45:22', '132456', '2022-10-07 17:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2051', '26', 'On', '132456', '2022-10-07 17:45:22', '132456', '2022-10-07 17:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2051', '29', 'On', '132456', '2022-10-07 17:45:22', '132456', '2022-10-07 17:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2051', '42', 'true', '132456', '2022-10-07 17:45:22', '132456', '2022-10-07 17:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2051', '43', 'true', '132456', '2022-10-07 17:45:22', '132456', '2022-10-07 17:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20511', '2051', '1', '2', '2022-10-07 17:45:22', '2022-10-07 17:45:22', '2022-11-06 17:45:22', NULL, NULL, '2592000000.0', '132456', '2022-10-07 17:45:22', '132456', '2022-10-07 17:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20512', '2051', '2', '2', '2022-10-07 17:45:22', '2022-10-07 17:45:22', '2022-11-06 17:45:22', NULL, NULL, '2592000000.0', '132456', '2022-10-07 17:45:22', '132456', '2022-10-07 17:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('205118', '2051', '18', '3', '2022-10-08 17:45:22', '2022-10-08 17:45:22', '2022-10-09 17:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-07 17:45:22', '132456', '2022-10-07 17:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20511', '2', '0', '132456', '2022-10-07 17:45:22', '132456', '2022-10-07 17:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20511', '5', 'No', '132456', '2022-10-07 17:45:22', '132456', '2022-10-07 17:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20512', '3', '0', '132456', '2022-10-07 17:45:22', '132456', '2022-10-07 17:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20512', '5', 'No', '132456', '2022-10-07 17:45:22', '132456', '2022-10-07 17:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('205118', '1', '30000419', '132456', '2022-10-07 17:45:22', '132456', '2022-10-07 17:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20511', '20512', '1', '1', '0', '132456', '2022-10-07 17:45:22', '132456', '2022-10-07 17:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20512', '205118', '0', '1', '0', '132456', '2022-10-07 17:45:22', '132456', '2022-10-07 17:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2051', '13', '2051', NULL, 132456, '132456', '2022-10-07 17:45:22', '132456', '2022-10-07 17:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2051', '1', '132456', '132456', '2022-10-07 17:45:22', '132456', '2022-10-07 17:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2051', '2', 'dok', '132456', '2022-10-07 17:45:22', '132456', '2022-10-07 17:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2052', '1', '39', '132456', '2022-10-04 02:45:22', '132456', '2022-10-04 02:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2052', '3', '1', '132456', '2022-10-04 02:45:22', '132456', '2022-10-04 02:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2052', '4', '0', '132456', '2022-10-04 02:45:22', '132456', '2022-10-04 02:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2052', '6', 'Development Challenge #2052', '132456', '2022-10-04 02:45:22', '132456', '2022-10-04 02:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2052', '7', '1.0', '132456', '2022-10-04 02:45:22', '132456', '2022-10-04 02:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2052', '9', 'On', '132456', '2022-10-04 02:45:22', '132456', '2022-10-04 02:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2052', '10', 'On', '132456', '2022-10-04 02:45:22', '132456', '2022-10-04 02:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2052', '11', 'On', '132456', '2022-10-04 02:45:22', '132456', '2022-10-04 02:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2052', '12', 'Yes', '132456', '2022-10-04 02:45:22', '132456', '2022-10-04 02:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2052', '13', 'Yes', '132456', '2022-10-04 02:45:22', '132456', '2022-10-04 02:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2052', '14', 'Open', '132456', '2022-10-04 02:45:22', '132456', '2022-10-04 02:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2052', '16', '0.0', '132456', '2022-10-04 02:45:22', '132456', '2022-10-04 02:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2052', '26', 'On', '132456', '2022-10-04 02:45:22', '132456', '2022-10-04 02:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2052', '29', 'On', '132456', '2022-10-04 02:45:22', '132456', '2022-10-04 02:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2052', '42', 'true', '132456', '2022-10-04 02:45:22', '132456', '2022-10-04 02:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2052', '43', 'true', '132456', '2022-10-04 02:45:22', '132456', '2022-10-04 02:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20521', '2052', '1', '3', '2022-10-04 02:45:22', '2022-10-04 02:45:22', '2022-10-06 02:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-04 02:45:22', '132456', '2022-10-04 02:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20522', '2052', '2', '3', '2022-10-04 02:45:22', '2022-10-04 02:45:22', '2022-10-06 02:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-04 02:45:22', '132456', '2022-10-04 02:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20524', '2052', '4', '3', '2022-10-06 02:45:22', '2022-10-06 02:45:22', '2022-10-07 02:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-04 02:45:22', '132456', '2022-10-04 02:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20525', '2052', '5', '3', '2022-10-07 02:45:22', '2022-10-07 02:45:22', '2022-10-08 02:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-04 02:45:22', '132456', '2022-10-04 02:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20526', '2052', '6', '3', '2022-10-08 02:45:22', '2022-10-08 02:45:22', '2022-10-08 14:45:22', NULL, NULL, '43200000.0', '132456', '2022-10-04 02:45:22', '132456', '2022-10-04 02:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20521', '2', '0', '132456', '2022-10-04 02:45:22', '132456', '2022-10-04 02:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20521', '5', 'No', '132456', '2022-10-04 02:45:22', '132456', '2022-10-04 02:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20522', '3', '0', '132456', '2022-10-04 02:45:22', '132456', '2022-10-04 02:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20522', '5', 'No', '132456', '2022-10-04 02:45:22', '132456', '2022-10-04 02:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20524', '5', 'No', '132456', '2022-10-04 02:45:22', '132456', '2022-10-04 02:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20524', '1', '30000413', '132456', '2022-10-04 02:45:22', '132456', '2022-10-04 02:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20525', '5', 'No', '132456', '2022-10-04 02:45:22', '132456', '2022-10-04 02:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20525', '4', 'No', '132456', '2022-10-04 02:45:22', '132456', '2022-10-04 02:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20526', '5', 'No', '132456', '2022-10-04 02:45:22', '132456', '2022-10-04 02:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20521', '20522', '1', '1', '0', '132456', '2022-10-04 02:45:22', '132456', '2022-10-04 02:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20522', '20524', '0', '1', '0', '132456', '2022-10-04 02:45:22', '132456', '2022-10-04 02:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20524', '20525', '0', '1', '0', '132456', '2022-10-04 02:45:22', '132456', '2022-10-04 02:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20525', '20526', '0', '1', '0', '132456', '2022-10-04 02:45:22', '132456', '2022-10-04 02:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2052', '13', '2052', NULL, 132456, '132456', '2022-10-04 02:45:22', '132456', '2022-10-04 02:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2052', '1', '132456', '132456', '2022-10-04 02:45:22', '132456', '2022-10-04 02:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2052', '2', 'dok', '132456', '2022-10-04 02:45:22', '132456', '2022-10-04 02:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2053', '1', '39', '132456', '2022-10-05 09:45:22', '132456', '2022-10-05 09:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2053', '3', '1', '132456', '2022-10-05 09:45:22', '132456', '2022-10-05 09:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2053', '4', '0', '132456', '2022-10-05 09:45:22', '132456', '2022-10-05 09:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2053', '6', 'Development Challenge #2053', '132456', '2022-10-05 09:45:22', '132456', '2022-10-05 09:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2053', '7', '1.0', '132456', '2022-10-05 09:45:22', '132456', '2022-10-05 09:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2053', '9', 'On', '132456', '2022-10-05 09:45:22', '132456', '2022-10-05 09:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2053', '10', 'On', '132456', '2022-10-05 09:45:22', '132456', '2022-10-05 09:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2053', '11', 'On', '132456', '2022-10-05 09:45:22', '132456', '2022-10-05 09:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2053', '12', 'Yes', '132456', '2022-10-05 09:45:22', '132456', '2022-10-05 09:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2053', '13', 'Yes', '132456', '2022-10-05 09:45:22', '132456', '2022-10-05 09:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2053', '14', 'Open', '132456', '2022-10-05 09:45:22', '132456', '2022-10-05 09:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2053', '16', '0.0', '132456', '2022-10-05 09:45:22', '132456', '2022-10-05 09:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2053', '26', 'On', '132456', '2022-10-05 09:45:22', '132456', '2022-10-05 09:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2053', '29', 'On', '132456', '2022-10-05 09:45:22', '132456', '2022-10-05 09:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2053', '42', 'true', '132456', '2022-10-05 09:45:22', '132456', '2022-10-05 09:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2053', '43', 'true', '132456', '2022-10-05 09:45:22', '132456', '2022-10-05 09:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20531', '2053', '1', '3', '2022-10-05 09:45:22', '2022-10-05 09:45:22', '2022-10-07 09:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-05 09:45:22', '132456', '2022-10-05 09:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20532', '2053', '2', '3', '2022-10-05 09:45:22', '2022-10-05 09:45:22', '2022-10-07 09:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-05 09:45:22', '132456', '2022-10-05 09:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20534', '2053', '4', '3', '2022-10-07 09:45:22', '2022-10-07 09:45:22', '2022-10-08 09:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-05 09:45:22', '132456', '2022-10-05 09:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20535', '2053', '5', '3', '2022-10-08 09:45:22', '2022-10-08 09:45:22', '2022-10-09 09:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-05 09:45:22', '132456', '2022-10-05 09:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20536', '2053', '6', '3', '2022-10-09 09:45:22', '2022-10-09 09:45:22', '2022-10-09 21:45:22', NULL, NULL, '43200000.0', '132456', '2022-10-05 09:45:22', '132456', '2022-10-05 09:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20531', '2', '0', '132456', '2022-10-05 09:45:22', '132456', '2022-10-05 09:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20531', '5', 'No', '132456', '2022-10-05 09:45:22', '132456', '2022-10-05 09:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20532', '3', '0', '132456', '2022-10-05 09:45:22', '132456', '2022-10-05 09:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20532', '5', 'No', '132456', '2022-10-05 09:45:22', '132456', '2022-10-05 09:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20534', '5', 'No', '132456', '2022-10-05 09:45:22', '132456', '2022-10-05 09:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20534', '1', '30000413', '132456', '2022-10-05 09:45:22', '132456', '2022-10-05 09:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20535', '5', 'No', '132456', '2022-10-05 09:45:22', '132456', '2022-10-05 09:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20535', '4', 'No', '132456', '2022-10-05 09:45:22', '132456', '2022-10-05 09:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20536', '5', 'No', '132456', '2022-10-05 09:45:22', '132456', '2022-10-05 09:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20531', '20532', '1', '1', '0', '132456', '2022-10-05 09:45:22', '132456', '2022-10-05 09:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20532', '20534', '0', '1', '0', '132456', '2022-10-05 09:45:22', '132456', '2022-10-05 09:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20534', '20535', '0', '1', '0', '132456', '2022-10-05 09:45:22', '132456', '2022-10-05 09:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20535', '20536', '0', '1', '0', '132456', '2022-10-05 09:45:22', '132456', '2022-10-05 09:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2053', '13', '2053', NULL, 132456, '132456', '2022-10-05 09:45:22', '132456', '2022-10-05 09:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2053', '1', '132456', '132456', '2022-10-05 09:45:22', '132456', '2022-10-05 09:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2053', '2', 'dok', '132456', '2022-10-05 09:45:22', '132456', '2022-10-05 09:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2054', '1', '40', '132456', '2022-10-08 14:45:22', '132456', '2022-10-08 14:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2054', '3', '1', '132456', '2022-10-08 14:45:22', '132456', '2022-10-08 14:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2054', '4', '0', '132456', '2022-10-08 14:45:22', '132456', '2022-10-08 14:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2054', '6', 'First2Finish Design Challenge #2054', '132456', '2022-10-08 14:45:22', '132456', '2022-10-08 14:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2054', '7', '1.0', '132456', '2022-10-08 14:45:22', '132456', '2022-10-08 14:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2054', '9', 'On', '132456', '2022-10-08 14:45:22', '132456', '2022-10-08 14:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2054', '10', 'On', '132456', '2022-10-08 14:45:22', '132456', '2022-10-08 14:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2054', '11', 'On', '132456', '2022-10-08 14:45:22', '132456', '2022-10-08 14:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2054', '12', 'Yes', '132456', '2022-10-08 14:45:22', '132456', '2022-10-08 14:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2054', '13', 'Yes', '132456', '2022-10-08 14:45:22', '132456', '2022-10-08 14:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2054', '14', 'Open', '132456', '2022-10-08 14:45:22', '132456', '2022-10-08 14:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2054', '16', '0.0', '132456', '2022-10-08 14:45:22', '132456', '2022-10-08 14:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2054', '26', 'On', '132456', '2022-10-08 14:45:22', '132456', '2022-10-08 14:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2054', '29', 'On', '132456', '2022-10-08 14:45:22', '132456', '2022-10-08 14:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2054', '42', 'true', '132456', '2022-10-08 14:45:22', '132456', '2022-10-08 14:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2054', '43', 'true', '132456', '2022-10-08 14:45:22', '132456', '2022-10-08 14:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20541', '2054', '1', '2', '2022-10-08 14:45:22', '2022-10-08 14:45:22', '2022-11-07 14:45:22', NULL, NULL, '2592000000.0', '132456', '2022-10-08 14:45:22', '132456', '2022-10-08 14:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20542', '2054', '2', '2', '2022-10-08 14:45:22', '2022-10-08 14:45:22', '2022-11-07 14:45:22', NULL, NULL, '2592000000.0', '132456', '2022-10-08 14:45:22', '132456', '2022-10-08 14:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('205418', '2054', '18', '3', '2022-10-09 14:45:22', '2022-10-09 14:45:22', '2022-10-10 14:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-08 14:45:22', '132456', '2022-10-08 14:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20541', '2', '0', '132456', '2022-10-08 14:45:22', '132456', '2022-10-08 14:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20541', '5', 'No', '132456', '2022-10-08 14:45:22', '132456', '2022-10-08 14:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20542', '3', '0', '132456', '2022-10-08 14:45:22', '132456', '2022-10-08 14:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20542', '5', 'No', '132456', '2022-10-08 14:45:22', '132456', '2022-10-08 14:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('205418', '1', '30000419', '132456', '2022-10-08 14:45:22', '132456', '2022-10-08 14:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20541', '20542', '1', '1', '0', '132456', '2022-10-08 14:45:22', '132456', '2022-10-08 14:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20542', '205418', '0', '1', '0', '132456', '2022-10-08 14:45:22', '132456', '2022-10-08 14:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2054', '13', '2054', NULL, 132456, '132456', '2022-10-08 14:45:22', '132456', '2022-10-08 14:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2054', '1', '132456', '132456', '2022-10-08 14:45:22', '132456', '2022-10-08 14:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2054', '2', 'dok', '132456', '2022-10-08 14:45:22', '132456', '2022-10-08 14:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2055', '1', '9', '132456', '2022-10-06 06:45:22', '132456', '2022-10-06 06:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2055', '3', '1', '132456', '2022-10-06 06:45:22', '132456', '2022-10-06 06:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2055', '4', '0', '132456', '2022-10-06 06:45:22', '132456', '2022-10-06 06:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2055', '6', 'QA Challenge #2055', '132456', '2022-10-06 06:45:22', '132456', '2022-10-06 06:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2055', '7', '1.0', '132456', '2022-10-06 06:45:22', '132456', '2022-10-06 06:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2055', '9', 'On', '132456', '2022-10-06 06:45:22', '132456', '2022-10-06 06:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2055', '10', 'On', '132456', '2022-10-06 06:45:22', '132456', '2022-10-06 06:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2055', '11', 'On', '132456', '2022-10-06 06:45:22', '132456', '2022-10-06 06:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2055', '12', 'Yes', '132456', '2022-10-06 06:45:22', '132456', '2022-10-06 06:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2055', '13', 'Yes', '132456', '2022-10-06 06:45:22', '132456', '2022-10-06 06:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2055', '14', 'Open', '132456', '2022-10-06 06:45:22', '132456', '2022-10-06 06:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2055', '16', '0.0', '132456', '2022-10-06 06:45:22', '132456', '2022-10-06 06:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2055', '26', 'On', '132456', '2022-10-06 06:45:22', '132456', '2022-10-06 06:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2055', '29', 'On', '132456', '2022-10-06 06:45:22', '132456', '2022-10-06 06:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2055', '42', 'true', '132456', '2022-10-06 06:45:22', '132456', '2022-10-06 06:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2055', '43', 'true', '132456', '2022-10-06 06:45:22', '132456', '2022-10-06 06:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20551', '2055', '1', '3', '2022-10-06 06:45:22', '2022-10-06 06:45:22', '2022-10-08 06:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-06 06:45:22', '132456', '2022-10-06 06:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20552', '2055', '2', '3', '2022-10-06 06:45:22', '2022-10-06 06:45:22', '2022-10-08 06:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-06 06:45:22', '132456', '2022-10-06 06:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20554', '2055', '4', '3', '2022-10-08 06:45:22', '2022-10-08 06:45:22', '2022-10-09 06:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-06 06:45:22', '132456', '2022-10-06 06:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20551', '2', '0', '132456', '2022-10-06 06:45:22', '132456', '2022-10-06 06:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20551', '5', 'No', '132456', '2022-10-06 06:45:22', '132456', '2022-10-06 06:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20552', '3', '0', '132456', '2022-10-06 06:45:22', '132456', '2022-10-06 06:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20552', '5', 'No', '132456', '2022-10-06 06:45:22', '132456', '2022-10-06 06:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20554', '5', 'No', '132456', '2022-10-06 06:45:22', '132456', '2022-10-06 06:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20554', '1', '30000413', '132456', '2022-10-06 06:45:22', '132456', '2022-10-06 06:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20551', '20552', '1', '1', '0', '132456', '2022-10-06 06:45:22', '132456', '2022-10-06 06:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20552', '20554', '0', '1', '0', '132456', '2022-10-06 06:45:22', '132456', '2022-10-06 06:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2055', '13', '2055', NULL, 132456, '132456', '2022-10-06 06:45:22', '132456', '2022-10-06 06:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2055', '1', '132456', '132456', '2022-10-06 06:45:22', '132456', '2022-10-06 06:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2055', '2', 'dok', '132456', '2022-10-06 06:45:22', '132456', '2022-10-06 06:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2056', '1', '39', '132456', '2022-10-09 16:45:22', '132456', '2022-10-09 16:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2056', '3', '1', '132456', '2022-10-09 16:45:22', '132456', '2022-10-09 16:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2056', '4', '0', '132456', '2022-10-09 16:45:22', '132456', '2022-10-09 16:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2056', '6', 'Development Challenge #2056', '132456', '2022-10-09 16:45:22', '132456', '2022-10-09 16:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2056', '7', '1.0', '132456', '2022-10-09 16:45:22', '132456', '2022-10-09 16:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2056', '9', 'On', '132456', '2022-10-09 16:45:22', '132456', '2022-10-09 16:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2056', '10', 'On', '132456', '2022-10-09 16:45:22', '132456', '2022-10-09 16:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2056', '11', 'On', '132456', '2022-10-09 16:45:22', '132456', '2022-10-09 16:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2056', '12', 'Yes', '132456', '2022-10-09 16:45:22', '132456', '2022-10-09 16:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2056', '13', 'Yes', '132456', '2022-10-09 16:45:22', '132456', '2022-10-09 16:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2056', '14', 'Open', '132456', '2022-10-09 16:45:22', '132456', '2022-10-09 16:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2056', '16', '0.0', '132456', '2022-10-09 16:45:22', '132456', '2022-10-09 16:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2056', '26', 'On', '132456', '2022-10-09 16:45:22', '132456', '2022-10-09 16:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2056', '29', 'On', '132456', '2022-10-09 16:45:22', '132456', '2022-10-09 16:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2056', '42', 'true', '132456', '2022-10-09 16:45:22', '132456', '2022-10-09 16:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2056', '43', 'true', '132456', '2022-10-09 16:45:22', '132456', '2022-10-09 16:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20561', '2056', '1', '2', '2022-10-09 16:45:22', '2022-10-09 16:45:22', '2022-10-11 16:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-09 16:45:22', '132456', '2022-10-09 16:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20562', '2056', '2', '2', '2022-10-09 16:45:22', '2022-10-09 16:45:22', '2022-10-11 16:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-09 16:45:22', '132456', '2022-10-09 16:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20564', '2056', '4', '1', '2022-10-11 16:45:22', '2022-10-11 16:45:22', '2022-10-12 16:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-09 16:45:22', '132456', '2022-10-09 16:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20565', '2056', '5', '1', '2022-10-12 16:45:22', '2022-10-12 16:45:22', '2022-10-13 16:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-09 16:45:22', '132456', '2022-10-09 16:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20566', '2056', '6', '1', '2022-10-13 16:45:22', '2022-10-13 16:45:22', '2022-10-14 04:45:22', NULL, NULL, '43200000.0', '132456', '2022-10-09 16:45:22', '132456', '2022-10-09 16:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20561', '2', '0', '132456', '2022-10-09 16:45:22', '132456', '2022-10-09 16:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20561', '5', 'No', '132456', '2022-10-09 16:45:22', '132456', '2022-10-09 16:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20562', '3', '0', '132456', '2022-10-09 16:45:22', '132456', '2022-10-09 16:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20562', '5', 'No', '132456', '2022-10-09 16:45:22', '132456', '2022-10-09 16:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20564', '5', 'No', '132456', '2022-10-09 16:45:22', '132456', '2022-10-09 16:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20564', '1', '30000413', '132456', '2022-10-09 16:45:22', '132456', '2022-10-09 16:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20565', '5', 'No', '132456', '2022-10-09 16:45:22', '132456', '2022-10-09 16:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20565', '4', 'No', '132456', '2022-10-09 16:45:22', '132456', '2022-10-09 16:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20566', '5', 'No', '132456', '2022-10-09 16:45:22', '132456', '2022-10-09 16:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20561', '20562', '1', '1', '0', '132456', '2022-10-09 16:45:22', '132456', '2022-10-09 16:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20562', '20564', '0', '1', '0', '132456', '2022-10-09 16:45:22', '132456', '2022-10-09 16:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20564', '20565', '0', '1', '0', '132456', '2022-10-09 16:45:22', '132456', '2022-10-09 16:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20565', '20566', '0', '1', '0', '132456', '2022-10-09 16:45:22', '132456', '2022-10-09 16:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2056', '13', '2056', NULL, 132456, '132456', '2022-10-09 16:45:22', '132456', '2022-10-09 16:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2056', '1', '132456', '132456', '2022-10-09 16:45:22', '132456', '2022-10-09 16:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2056', '2', 'dok', '132456', '2022-10-09 16:45:22', '132456', '2022-10-09 16:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2057', '1', '17', '132456', '2022-10-05 18:45:22', '132456', '2022-10-05 18:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2057', '3', '1', '132456', '2022-10-05 18:45:22', '132456', '2022-10-05 18:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2057', '4', '0', '132456', '2022-10-05 18:45:22', '132456', '2022-10-05 18:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2057', '6', 'Design Challenge #2057', '132456', '2022-10-05 18:45:22', '132456', '2022-10-05 18:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2057', '7', '1.0', '132456', '2022-10-05 18:45:22', '132456', '2022-10-05 18:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2057', '9', 'On', '132456', '2022-10-05 18:45:22', '132456', '2022-10-05 18:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2057', '10', 'On', '132456', '2022-10-05 18:45:22', '132456', '2022-10-05 18:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2057', '11', 'On', '132456', '2022-10-05 18:45:22', '132456', '2022-10-05 18:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2057', '12', 'Yes', '132456', '2022-10-05 18:45:22', '132456', '2022-10-05 18:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2057', '13', 'Yes', '132456', '2022-10-05 18:45:22', '132456', '2022-10-05 18:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2057', '14', 'Open', '132456', '2022-10-05 18:45:22', '132456', '2022-10-05 18:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2057', '16', '0.0', '132456', '2022-10-05 18:45:22', '132456', '2022-10-05 18:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2057', '26', 'On', '132456', '2022-10-05 18:45:22', '132456', '2022-10-05 18:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2057', '29', 'On', '132456', '2022-10-05 18:45:22', '132456', '2022-10-05 18:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2057', '42', 'true', '132456', '2022-10-05 18:45:22', '132456', '2022-10-05 18:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2057', '43', 'true', '132456', '2022-10-05 18:45:22', '132456', '2022-10-05 18:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20571', '2057', '1', '3', '2022-10-05 18:45:22', '2022-10-05 18:45:22', '2022-10-07 18:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-05 18:45:22', '132456', '2022-10-05 18:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20572', '2057', '2', '3', '2022-10-05 18:45:22', '2022-10-05 18:45:22', '2022-10-07 18:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-05 18:45:22', '132456', '2022-10-05 18:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20573', '2057', '3', '3', '2022-10-07 18:45:22', '2022-10-07 18:45:22', '2022-10-08 06:45:22', NULL, NULL, '43200000.0', '132456', '2022-10-05 18:45:22', '132456', '2022-10-05 18:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20574', '2057', '4', '3', '2022-10-08 06:45:22', '2022-10-08 06:45:22', '2022-10-09 06:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-05 18:45:22', '132456', '2022-10-05 18:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('205711', '2057', '11', '3', '2022-10-09 06:45:22', '2022-10-09 06:45:22', '2022-10-09 18:45:22', NULL, NULL, '43200000.0', '132456', '2022-10-05 18:45:22', '132456', '2022-10-05 18:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20571', '2', '0', '132456', '2022-10-05 18:45:22', '132456', '2022-10-05 18:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20571', '5', 'No', '132456', '2022-10-05 18:45:22', '132456', '2022-10-05 18:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20572', '3', '0', '132456', '2022-10-05 18:45:22', '132456', '2022-10-05 18:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20572', '5', 'No', '132456', '2022-10-05 18:45:22', '132456', '2022-10-05 18:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20573', '1', '30000410', '132456', '2022-10-05 18:45:22', '132456', '2022-10-05 18:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20574', '5', 'No', '132456', '2022-10-05 18:45:22', '132456', '2022-10-05 18:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20574', '1', '30000411', '132456', '2022-10-05 18:45:22', '132456', '2022-10-05 18:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('205711', '1', '30000720', '132456', '2022-10-05 18:45:22', '132456', '2022-10-05 18:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20571', '20572', '1', '1', '0', '132456', '2022-10-05 18:45:22', '132456', '2022-10-05 18:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20572', '20573', '0', '1', '0', '132456', '2022-10-05 18:45:22', '132456', '2022-10-05 18:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20573', '20574', '0', '1', '0', '132456', '2022-10-05 18:45:22', '132456', '2022-10-05 18:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20574', '205711', '0', '1', '0', '132456', '2022-10-05 18:45:22', '132456', '2022-10-05 18:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2057', '13', '2057', NULL, 132456, '132456', '2022-10-05 18:45:22', '132456', '2022-10-05 18:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2057', '1', '132456', '132456', '2022-10-05 18:45:22', '132456', '2022-10-05 18:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2057', '2', 'dok', '132456', '2022-10-05 18:45:22', '132456', '2022-10-05 18:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2058', '1', '37', '132456', '2022-10-05 01:45:22', '132456', '2022-10-05 01:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2058', '3', '1', '132456', '2022-10-05 01:45:22', '132456', '2022-10-05 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2058', '4', '0', '132456', '2022-10-05 01:45:22', '132456', '2022-10-05 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2058', '6', 'Data Science Challenge #2058', '132456', '2022-10-05 01:45:22', '132456', '2022-10-05 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2058', '7', '1.0', '132456', '2022-10-05 01:45:22', '132456', '2022-10-05 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2058', '9', 'On', '132456', '2022-10-05 01:45:22', '132456', '2022-10-05 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2058', '10', 'On', '132456', '2022-10-05 01:45:22', '132456', '2022-10-05 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2058', '11', 'On', '132456', '2022-10-05 01:45:22', '132456', '2022-10-05 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2058', '12', 'Yes', '132456', '2022-10-05 01:45:22', '132456', '2022-10-05 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2058', '13', 'Yes', '132456', '2022-10-05 01:45:22', '132456', '2022-10-05 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2058', '14', 'Open', '132456', '2022-10-05 01:45:22', '132456', '2022-10-05 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2058', '16', '0.0', '132456', '2022-10-05 01:45:22', '132456', '2022-10-05 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2058', '26', 'On', '132456', '2022-10-05 01:45:22', '132456', '2022-10-05 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2058', '29', 'On', '132456', '2022-10-05 01:45:22', '132456', '2022-10-05 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2058', '42', 'true', '132456', '2022-10-05 01:45:22', '132456', '2022-10-05 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2058', '43', 'true', '132456', '2022-10-05 01:45:22', '132456', '2022-10-05 01:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20581', '2058', '1', '3', '2022-10-05 01:45:22', '2022-10-05 01:45:22', '2022-10-07 01:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-05 01:45:22', '132456', '2022-10-05 01:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20582', '2058', '2', '3', '2022-10-05 01:45:22', '2022-10-05 01:45:22', '2022-10-07 01:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-05 01:45:22', '132456', '2022-10-05 01:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20584', '2058', '4', '3', '2022-10-07 01:45:22', '2022-10-07 01:45:22', '2022-10-08 01:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-05 01:45:22', '132456', '2022-10-05 01:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20581', '2', '0', '132456', '2022-10-05 01:45:22', '132456', '2022-10-05 01:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20581', '5', 'No', '132456', '2022-10-05 01:45:22', '132456', '2022-10-05 01:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20582', '3', '0', '132456', '2022-10-05 01:45:22', '132456', '2022-10-05 01:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20582', '5', 'No', '132456', '2022-10-05 01:45:22', '132456', '2022-10-05 01:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20584', '5', 'No', '132456', '2022-10-05 01:45:22', '132456', '2022-10-05 01:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20584', '1', '30000413', '132456', '2022-10-05 01:45:22', '132456', '2022-10-05 01:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20581', '20582', '1', '1', '0', '132456', '2022-10-05 01:45:22', '132456', '2022-10-05 01:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20582', '20584', '0', '1', '0', '132456', '2022-10-05 01:45:22', '132456', '2022-10-05 01:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2058', '13', '2058', NULL, 132456, '132456', '2022-10-05 01:45:22', '132456', '2022-10-05 01:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2058', '1', '132456', '132456', '2022-10-05 01:45:22', '132456', '2022-10-05 01:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2058', '2', 'dok', '132456', '2022-10-05 01:45:22', '132456', '2022-10-05 01:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2059', '1', '37', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2059', '3', '1', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2059', '4', '0', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2059', '6', 'Data Science Challenge #2059', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2059', '7', '1.0', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2059', '9', 'On', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2059', '10', 'On', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2059', '11', 'On', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2059', '12', 'Yes', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2059', '13', 'Yes', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2059', '14', 'Open', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2059', '16', '0.0', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2059', '26', 'On', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2059', '29', 'On', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2059', '42', 'true', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2059', '43', 'true', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20591', '2059', '1', '3', '2022-10-07 21:45:22', '2022-10-07 21:45:22', '2022-10-09 21:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20592', '2059', '2', '3', '2022-10-07 21:45:22', '2022-10-07 21:45:22', '2022-10-09 21:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20594', '2059', '4', '3', '2022-10-09 21:45:22', '2022-10-09 21:45:22', '2022-10-10 21:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20591', '2', '0', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20591', '5', 'No', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20592', '3', '0', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20592', '5', 'No', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20594', '5', 'No', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20594', '1', '30000413', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20591', '20592', '1', '1', '0', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20592', '20594', '0', '1', '0', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2059', '13', '2059', NULL, 132456, '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2059', '1', '132456', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2059', '2', 'dok', '132456', '2022-10-07 21:45:22', '132456', '2022-10-07 21:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2060', '1', '17', '132456', '2022-10-04 12:45:22', '132456', '2022-10-04 12:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2060', '3', '1', '132456', '2022-10-04 12:45:22', '132456', '2022-10-04 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2060', '4', '0', '132456', '2022-10-04 12:45:22', '132456', '2022-10-04 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2060', '6', 'Design Challenge #2060', '132456', '2022-10-04 12:45:22', '132456', '2022-10-04 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2060', '7', '1.0', '132456', '2022-10-04 12:45:22', '132456', '2022-10-04 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2060', '9', 'On', '132456', '2022-10-04 12:45:22', '132456', '2022-10-04 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2060', '10', 'On', '132456', '2022-10-04 12:45:22', '132456', '2022-10-04 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2060', '11', 'On', '132456', '2022-10-04 12:45:22', '132456', '2022-10-04 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2060', '12', 'Yes', '132456', '2022-10-04 12:45:22', '132456', '2022-10-04 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2060', '13', 'Yes', '132456', '2022-10-04 12:45:22', '132456', '2022-10-04 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2060', '14', 'Open', '132456', '2022-10-04 12:45:22', '132456', '2022-10-04 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2060', '16', '0.0', '132456', '2022-10-04 12:45:22', '132456', '2022-10-04 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2060', '26', 'On', '132456', '2022-10-04 12:45:22', '132456', '2022-10-04 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2060', '29', 'On', '132456', '2022-10-04 12:45:22', '132456', '2022-10-04 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2060', '42', 'true', '132456', '2022-10-04 12:45:22', '132456', '2022-10-04 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2060', '43', 'true', '132456', '2022-10-04 12:45:22', '132456', '2022-10-04 12:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20601', '2060', '1', '3', '2022-10-04 12:45:22', '2022-10-04 12:45:22', '2022-10-06 12:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-04 12:45:22', '132456', '2022-10-04 12:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20602', '2060', '2', '3', '2022-10-04 12:45:22', '2022-10-04 12:45:22', '2022-10-06 12:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-04 12:45:22', '132456', '2022-10-04 12:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20603', '2060', '3', '3', '2022-10-06 12:45:22', '2022-10-06 12:45:22', '2022-10-07 00:45:22', NULL, NULL, '43200000.0', '132456', '2022-10-04 12:45:22', '132456', '2022-10-04 12:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20604', '2060', '4', '3', '2022-10-07 00:45:22', '2022-10-07 00:45:22', '2022-10-08 00:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-04 12:45:22', '132456', '2022-10-04 12:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('206011', '2060', '11', '3', '2022-10-08 00:45:22', '2022-10-08 00:45:22', '2022-10-08 12:45:22', NULL, NULL, '43200000.0', '132456', '2022-10-04 12:45:22', '132456', '2022-10-04 12:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20601', '2', '0', '132456', '2022-10-04 12:45:22', '132456', '2022-10-04 12:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20601', '5', 'No', '132456', '2022-10-04 12:45:22', '132456', '2022-10-04 12:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20602', '3', '0', '132456', '2022-10-04 12:45:22', '132456', '2022-10-04 12:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20602', '5', 'No', '132456', '2022-10-04 12:45:22', '132456', '2022-10-04 12:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20603', '1', '30000410', '132456', '2022-10-04 12:45:22', '132456', '2022-10-04 12:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20604', '5', 'No', '132456', '2022-10-04 12:45:22', '132456', '2022-10-04 12:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20604', '1', '30000411', '132456', '2022-10-04 12:45:22', '132456', '2022-10-04 12:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('206011', '1', '30000720', '132456', '2022-10-04 12:45:22', '132456', '2022-10-04 12:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20601', '20602', '1', '1', '0', '132456', '2022-10-04 12:45:22', '132456', '2022-10-04 12:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20602', '20603', '0', '1', '0', '132456', '2022-10-04 12:45:22', '132456', '2022-10-04 12:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20603', '20604', '0', '1', '0', '132456', '2022-10-04 12:45:22', '132456', '2022-10-04 12:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20604', '206011', '0', '1', '0', '132456', '2022-10-04 12:45:22', '132456', '2022-10-04 12:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2060', '13', '2060', NULL, 132456, '132456', '2022-10-04 12:45:22', '132456', '2022-10-04 12:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2060', '1', '132456', '132456', '2022-10-04 12:45:22', '132456', '2022-10-04 12:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2060', '2', 'dok', '132456', '2022-10-04 12:45:22', '132456', '2022-10-04 12:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2061', '1', '37', '132456', '2022-10-06 17:45:22', '132456', '2022-10-06 17:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2061', '3', '1', '132456', '2022-10-06 17:45:22', '132456', '2022-10-06 17:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2061', '4', '0', '132456', '2022-10-06 17:45:22', '132456', '2022-10-06 17:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2061', '6', 'Data Science Challenge #2061', '132456', '2022-10-06 17:45:22', '132456', '2022-10-06 17:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2061', '7', '1.0', '132456', '2022-10-06 17:45:22', '132456', '2022-10-06 17:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2061', '9', 'On', '132456', '2022-10-06 17:45:22', '132456', '2022-10-06 17:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2061', '10', 'On', '132456', '2022-10-06 17:45:22', '132456', '2022-10-06 17:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2061', '11', 'On', '132456', '2022-10-06 17:45:22', '132456', '2022-10-06 17:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2061', '12', 'Yes', '132456', '2022-10-06 17:45:22', '132456', '2022-10-06 17:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2061', '13', 'Yes', '132456', '2022-10-06 17:45:22', '132456', '2022-10-06 17:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2061', '14', 'Open', '132456', '2022-10-06 17:45:22', '132456', '2022-10-06 17:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2061', '16', '0.0', '132456', '2022-10-06 17:45:22', '132456', '2022-10-06 17:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2061', '26', 'On', '132456', '2022-10-06 17:45:22', '132456', '2022-10-06 17:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2061', '29', 'On', '132456', '2022-10-06 17:45:22', '132456', '2022-10-06 17:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2061', '42', 'true', '132456', '2022-10-06 17:45:22', '132456', '2022-10-06 17:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2061', '43', 'true', '132456', '2022-10-06 17:45:22', '132456', '2022-10-06 17:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20611', '2061', '1', '3', '2022-10-06 17:45:22', '2022-10-06 17:45:22', '2022-10-08 17:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-06 17:45:22', '132456', '2022-10-06 17:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20612', '2061', '2', '3', '2022-10-06 17:45:22', '2022-10-06 17:45:22', '2022-10-08 17:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-06 17:45:22', '132456', '2022-10-06 17:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20614', '2061', '4', '3', '2022-10-08 17:45:22', '2022-10-08 17:45:22', '2022-10-09 17:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-06 17:45:22', '132456', '2022-10-06 17:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20611', '2', '0', '132456', '2022-10-06 17:45:22', '132456', '2022-10-06 17:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20611', '5', 'No', '132456', '2022-10-06 17:45:22', '132456', '2022-10-06 17:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20612', '3', '0', '132456', '2022-10-06 17:45:22', '132456', '2022-10-06 17:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20612', '5', 'No', '132456', '2022-10-06 17:45:22', '132456', '2022-10-06 17:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20614', '5', 'No', '132456', '2022-10-06 17:45:22', '132456', '2022-10-06 17:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20614', '1', '30000413', '132456', '2022-10-06 17:45:22', '132456', '2022-10-06 17:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20611', '20612', '1', '1', '0', '132456', '2022-10-06 17:45:22', '132456', '2022-10-06 17:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20612', '20614', '0', '1', '0', '132456', '2022-10-06 17:45:22', '132456', '2022-10-06 17:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2061', '13', '2061', NULL, 132456, '132456', '2022-10-06 17:45:22', '132456', '2022-10-06 17:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2061', '1', '132456', '132456', '2022-10-06 17:45:22', '132456', '2022-10-06 17:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2061', '2', 'dok', '132456', '2022-10-06 17:45:22', '132456', '2022-10-06 17:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2062', '1', '9', '132456', '2022-10-06 12:45:22', '132456', '2022-10-06 12:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2062', '3', '1', '132456', '2022-10-06 12:45:22', '132456', '2022-10-06 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2062', '4', '0', '132456', '2022-10-06 12:45:22', '132456', '2022-10-06 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2062', '6', 'QA Challenge #2062', '132456', '2022-10-06 12:45:22', '132456', '2022-10-06 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2062', '7', '1.0', '132456', '2022-10-06 12:45:22', '132456', '2022-10-06 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2062', '9', 'On', '132456', '2022-10-06 12:45:22', '132456', '2022-10-06 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2062', '10', 'On', '132456', '2022-10-06 12:45:22', '132456', '2022-10-06 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2062', '11', 'On', '132456', '2022-10-06 12:45:22', '132456', '2022-10-06 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2062', '12', 'Yes', '132456', '2022-10-06 12:45:22', '132456', '2022-10-06 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2062', '13', 'Yes', '132456', '2022-10-06 12:45:22', '132456', '2022-10-06 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2062', '14', 'Open', '132456', '2022-10-06 12:45:22', '132456', '2022-10-06 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2062', '16', '0.0', '132456', '2022-10-06 12:45:22', '132456', '2022-10-06 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2062', '26', 'On', '132456', '2022-10-06 12:45:22', '132456', '2022-10-06 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2062', '29', 'On', '132456', '2022-10-06 12:45:22', '132456', '2022-10-06 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2062', '42', 'true', '132456', '2022-10-06 12:45:22', '132456', '2022-10-06 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2062', '43', 'true', '132456', '2022-10-06 12:45:22', '132456', '2022-10-06 12:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20621', '2062', '1', '3', '2022-10-06 12:45:22', '2022-10-06 12:45:22', '2022-10-08 12:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-06 12:45:22', '132456', '2022-10-06 12:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20622', '2062', '2', '3', '2022-10-06 12:45:22', '2022-10-06 12:45:22', '2022-10-08 12:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-06 12:45:22', '132456', '2022-10-06 12:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20624', '2062', '4', '3', '2022-10-08 12:45:22', '2022-10-08 12:45:22', '2022-10-09 12:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-06 12:45:22', '132456', '2022-10-06 12:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20621', '2', '0', '132456', '2022-10-06 12:45:22', '132456', '2022-10-06 12:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20621', '5', 'No', '132456', '2022-10-06 12:45:22', '132456', '2022-10-06 12:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20622', '3', '0', '132456', '2022-10-06 12:45:22', '132456', '2022-10-06 12:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20622', '5', 'No', '132456', '2022-10-06 12:45:22', '132456', '2022-10-06 12:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20624', '5', 'No', '132456', '2022-10-06 12:45:22', '132456', '2022-10-06 12:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20624', '1', '30000413', '132456', '2022-10-06 12:45:22', '132456', '2022-10-06 12:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20621', '20622', '1', '1', '0', '132456', '2022-10-06 12:45:22', '132456', '2022-10-06 12:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20622', '20624', '0', '1', '0', '132456', '2022-10-06 12:45:22', '132456', '2022-10-06 12:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2062', '13', '2062', NULL, 132456, '132456', '2022-10-06 12:45:22', '132456', '2022-10-06 12:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2062', '1', '132456', '132456', '2022-10-06 12:45:22', '132456', '2022-10-06 12:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2062', '2', 'dok', '132456', '2022-10-06 12:45:22', '132456', '2022-10-06 12:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2063', '1', '17', '132456', '2022-10-09 03:45:22', '132456', '2022-10-09 03:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2063', '3', '1', '132456', '2022-10-09 03:45:22', '132456', '2022-10-09 03:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2063', '4', '0', '132456', '2022-10-09 03:45:22', '132456', '2022-10-09 03:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2063', '6', 'Design Challenge #2063', '132456', '2022-10-09 03:45:22', '132456', '2022-10-09 03:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2063', '7', '1.0', '132456', '2022-10-09 03:45:22', '132456', '2022-10-09 03:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2063', '9', 'On', '132456', '2022-10-09 03:45:22', '132456', '2022-10-09 03:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2063', '10', 'On', '132456', '2022-10-09 03:45:22', '132456', '2022-10-09 03:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2063', '11', 'On', '132456', '2022-10-09 03:45:22', '132456', '2022-10-09 03:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2063', '12', 'Yes', '132456', '2022-10-09 03:45:22', '132456', '2022-10-09 03:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2063', '13', 'Yes', '132456', '2022-10-09 03:45:22', '132456', '2022-10-09 03:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2063', '14', 'Open', '132456', '2022-10-09 03:45:22', '132456', '2022-10-09 03:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2063', '16', '0.0', '132456', '2022-10-09 03:45:22', '132456', '2022-10-09 03:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2063', '26', 'On', '132456', '2022-10-09 03:45:22', '132456', '2022-10-09 03:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2063', '29', 'On', '132456', '2022-10-09 03:45:22', '132456', '2022-10-09 03:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2063', '42', 'true', '132456', '2022-10-09 03:45:22', '132456', '2022-10-09 03:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2063', '43', 'true', '132456', '2022-10-09 03:45:22', '132456', '2022-10-09 03:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20631', '2063', '1', '2', '2022-10-09 03:45:22', '2022-10-09 03:45:22', '2022-10-11 03:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-09 03:45:22', '132456', '2022-10-09 03:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20632', '2063', '2', '2', '2022-10-09 03:45:22', '2022-10-09 03:45:22', '2022-10-11 03:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-09 03:45:22', '132456', '2022-10-09 03:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20633', '2063', '3', '1', '2022-10-11 03:45:22', '2022-10-11 03:45:22', '2022-10-11 15:45:22', NULL, NULL, '43200000.0', '132456', '2022-10-09 03:45:22', '132456', '2022-10-09 03:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20634', '2063', '4', '1', '2022-10-11 15:45:22', '2022-10-11 15:45:22', '2022-10-12 15:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-09 03:45:22', '132456', '2022-10-09 03:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('206311', '2063', '11', '1', '2022-10-12 15:45:22', '2022-10-12 15:45:22', '2022-10-13 03:45:22', NULL, NULL, '43200000.0', '132456', '2022-10-09 03:45:22', '132456', '2022-10-09 03:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20631', '2', '0', '132456', '2022-10-09 03:45:22', '132456', '2022-10-09 03:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20631', '5', 'No', '132456', '2022-10-09 03:45:22', '132456', '2022-10-09 03:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20632', '3', '0', '132456', '2022-10-09 03:45:22', '132456', '2022-10-09 03:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20632', '5', 'No', '132456', '2022-10-09 03:45:22', '132456', '2022-10-09 03:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20633', '1', '30000410', '132456', '2022-10-09 03:45:22', '132456', '2022-10-09 03:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20634', '5', 'No', '132456', '2022-10-09 03:45:22', '132456', '2022-10-09 03:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20634', '1', '30000411', '132456', '2022-10-09 03:45:22', '132456', '2022-10-09 03:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('206311', '1', '30000720', '132456', '2022-10-09 03:45:22', '132456', '2022-10-09 03:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20631', '20632', '1', '1', '0', '132456', '2022-10-09 03:45:22', '132456', '2022-10-09 03:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20632', '20633', '0', '1', '0', '132456', '2022-10-09 03:45:22', '132456', '2022-10-09 03:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20633', '20634', '0', '1', '0', '132456', '2022-10-09 03:45:22', '132456', '2022-10-09 03:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20634', '206311', '0', '1', '0', '132456', '2022-10-09 03:45:22', '132456', '2022-10-09 03:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2063', '13', '2063', NULL, 132456, '132456', '2022-10-09 03:45:22', '132456', '2022-10-09 03:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2063', '1', '132456', '132456', '2022-10-09 03:45:22', '132456', '2022-10-09 03:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2063', '2', 'dok', '132456', '2022-10-09 03:45:22', '132456', '2022-10-09 03:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2064', '1', '37', '132456', '2022-10-10 07:45:22', '132456', '2022-10-10 07:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2064', '3', '1', '132456', '2022-10-10 07:45:22', '132456', '2022-10-10 07:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2064', '4', '0', '132456', '2022-10-10 07:45:22', '132456', '2022-10-10 07:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2064', '6', 'Data Science Challenge #2064', '132456', '2022-10-10 07:45:22', '132456', '2022-10-10 07:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2064', '7', '1.0', '132456', '2022-10-10 07:45:22', '132456', '2022-10-10 07:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2064', '9', 'On', '132456', '2022-10-10 07:45:22', '132456', '2022-10-10 07:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2064', '10', 'On', '132456', '2022-10-10 07:45:22', '132456', '2022-10-10 07:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2064', '11', 'On', '132456', '2022-10-10 07:45:22', '132456', '2022-10-10 07:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2064', '12', 'Yes', '132456', '2022-10-10 07:45:22', '132456', '2022-10-10 07:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2064', '13', 'Yes', '132456', '2022-10-10 07:45:22', '132456', '2022-10-10 07:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2064', '14', 'Open', '132456', '2022-10-10 07:45:22', '132456', '2022-10-10 07:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2064', '16', '0.0', '132456', '2022-10-10 07:45:22', '132456', '2022-10-10 07:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2064', '26', 'On', '132456', '2022-10-10 07:45:22', '132456', '2022-10-10 07:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2064', '29', 'On', '132456', '2022-10-10 07:45:22', '132456', '2022-10-10 07:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2064', '42', 'true', '132456', '2022-10-10 07:45:22', '132456', '2022-10-10 07:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2064', '43', 'true', '132456', '2022-10-10 07:45:22', '132456', '2022-10-10 07:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20641', '2064', '1', '2', '2022-10-10 07:45:22', '2022-10-10 07:45:22', '2022-10-12 07:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-10 07:45:22', '132456', '2022-10-10 07:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20642', '2064', '2', '2', '2022-10-10 07:45:22', '2022-10-10 07:45:22', '2022-10-12 07:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-10 07:45:22', '132456', '2022-10-10 07:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20644', '2064', '4', '1', '2022-10-12 07:45:22', '2022-10-12 07:45:22', '2022-10-13 07:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-10 07:45:22', '132456', '2022-10-10 07:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20641', '2', '0', '132456', '2022-10-10 07:45:22', '132456', '2022-10-10 07:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20641', '5', 'No', '132456', '2022-10-10 07:45:22', '132456', '2022-10-10 07:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20642', '3', '0', '132456', '2022-10-10 07:45:22', '132456', '2022-10-10 07:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20642', '5', 'No', '132456', '2022-10-10 07:45:22', '132456', '2022-10-10 07:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20644', '5', 'No', '132456', '2022-10-10 07:45:22', '132456', '2022-10-10 07:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20644', '1', '30000413', '132456', '2022-10-10 07:45:22', '132456', '2022-10-10 07:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20641', '20642', '1', '1', '0', '132456', '2022-10-10 07:45:22', '132456', '2022-10-10 07:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20642', '20644', '0', '1', '0', '132456', '2022-10-10 07:45:22', '132456', '2022-10-10 07:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2064', '13', '2064', NULL, 132456, '132456', '2022-10-10 07:45:22', '132456', '2022-10-10 07:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2064', '1', '132456', '132456', '2022-10-10 07:45:22', '132456', '2022-10-10 07:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2064', '2', 'dok', '132456', '2022-10-10 07:45:22', '132456', '2022-10-10 07:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2065', '1', '38', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2065', '3', '1', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2065', '4', '0', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2065', '6', 'First2Finish Challenge #2065', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2065', '7', '1.0', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2065', '9', 'On', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2065', '10', 'On', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2065', '11', 'On', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2065', '12', 'Yes', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2065', '13', 'Yes', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2065', '14', 'Open', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2065', '16', '0.0', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2065', '26', 'On', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2065', '29', 'On', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2065', '42', 'true', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2065', '43', 'true', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20651', '2065', '1', '2', '2022-10-09 13:45:22', '2022-10-09 13:45:22', '2022-11-08 13:45:22', NULL, NULL, '2592000000.0', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20652', '2065', '2', '2', '2022-10-09 13:45:22', '2022-10-09 13:45:22', '2022-11-08 13:45:22', NULL, NULL, '2592000000.0', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('206518', '2065', '18', '2', '2022-10-10 13:45:22', '2022-10-10 13:45:22', '2022-10-11 13:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20651', '2', '0', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20651', '5', 'No', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20652', '3', '0', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20652', '5', 'No', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('206518', '1', '30000419', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20651', '20652', '1', '1', '0', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20652', '206518', '0', '1', '0', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2065', '13', '2065', NULL, 132456, '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2065', '1', '132456', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2065', '2', 'dok', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2066', '1', '37', '132456', '2022-10-09 09:45:22', '132456', '2022-10-09 09:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2066', '3', '1', '132456', '2022-10-09 09:45:22', '132456', '2022-10-09 09:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2066', '4', '0', '132456', '2022-10-09 09:45:22', '132456', '2022-10-09 09:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2066', '6', 'Data Science Challenge #2066', '132456', '2022-10-09 09:45:22', '132456', '2022-10-09 09:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2066', '7', '1.0', '132456', '2022-10-09 09:45:22', '132456', '2022-10-09 09:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2066', '9', 'On', '132456', '2022-10-09 09:45:22', '132456', '2022-10-09 09:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2066', '10', 'On', '132456', '2022-10-09 09:45:22', '132456', '2022-10-09 09:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2066', '11', 'On', '132456', '2022-10-09 09:45:22', '132456', '2022-10-09 09:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2066', '12', 'Yes', '132456', '2022-10-09 09:45:22', '132456', '2022-10-09 09:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2066', '13', 'Yes', '132456', '2022-10-09 09:45:22', '132456', '2022-10-09 09:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2066', '14', 'Open', '132456', '2022-10-09 09:45:22', '132456', '2022-10-09 09:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2066', '16', '0.0', '132456', '2022-10-09 09:45:22', '132456', '2022-10-09 09:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2066', '26', 'On', '132456', '2022-10-09 09:45:22', '132456', '2022-10-09 09:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2066', '29', 'On', '132456', '2022-10-09 09:45:22', '132456', '2022-10-09 09:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2066', '42', 'true', '132456', '2022-10-09 09:45:22', '132456', '2022-10-09 09:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2066', '43', 'true', '132456', '2022-10-09 09:45:22', '132456', '2022-10-09 09:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20661', '2066', '1', '2', '2022-10-09 09:45:22', '2022-10-09 09:45:22', '2022-10-11 09:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-09 09:45:22', '132456', '2022-10-09 09:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20662', '2066', '2', '2', '2022-10-09 09:45:22', '2022-10-09 09:45:22', '2022-10-11 09:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-09 09:45:22', '132456', '2022-10-09 09:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20664', '2066', '4', '1', '2022-10-11 09:45:22', '2022-10-11 09:45:22', '2022-10-12 09:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-09 09:45:22', '132456', '2022-10-09 09:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20661', '2', '0', '132456', '2022-10-09 09:45:22', '132456', '2022-10-09 09:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20661', '5', 'No', '132456', '2022-10-09 09:45:22', '132456', '2022-10-09 09:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20662', '3', '0', '132456', '2022-10-09 09:45:22', '132456', '2022-10-09 09:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20662', '5', 'No', '132456', '2022-10-09 09:45:22', '132456', '2022-10-09 09:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20664', '5', 'No', '132456', '2022-10-09 09:45:22', '132456', '2022-10-09 09:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20664', '1', '30000413', '132456', '2022-10-09 09:45:22', '132456', '2022-10-09 09:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20661', '20662', '1', '1', '0', '132456', '2022-10-09 09:45:22', '132456', '2022-10-09 09:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20662', '20664', '0', '1', '0', '132456', '2022-10-09 09:45:22', '132456', '2022-10-09 09:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2066', '13', '2066', NULL, 132456, '132456', '2022-10-09 09:45:22', '132456', '2022-10-09 09:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2066', '1', '132456', '132456', '2022-10-09 09:45:22', '132456', '2022-10-09 09:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2066', '2', 'dok', '132456', '2022-10-09 09:45:22', '132456', '2022-10-09 09:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2067', '1', '9', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2067', '3', '1', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2067', '4', '0', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2067', '6', 'QA Challenge #2067', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2067', '7', '1.0', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2067', '9', 'On', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2067', '10', 'On', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2067', '11', 'On', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2067', '12', 'Yes', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2067', '13', 'Yes', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2067', '14', 'Open', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2067', '16', '0.0', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2067', '26', 'On', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2067', '29', 'On', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2067', '42', 'true', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2067', '43', 'true', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20671', '2067', '1', '3', '2022-10-07 08:45:22', '2022-10-07 08:45:22', '2022-10-09 08:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20672', '2067', '2', '3', '2022-10-07 08:45:22', '2022-10-07 08:45:22', '2022-10-09 08:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20674', '2067', '4', '3', '2022-10-09 08:45:22', '2022-10-09 08:45:22', '2022-10-10 08:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20671', '2', '0', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20671', '5', 'No', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20672', '3', '0', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20672', '5', 'No', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20674', '5', 'No', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20674', '1', '30000413', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20671', '20672', '1', '1', '0', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20672', '20674', '0', '1', '0', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2067', '13', '2067', NULL, 132456, '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2067', '1', '132456', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2067', '2', 'dok', '132456', '2022-10-07 08:45:22', '132456', '2022-10-07 08:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2068', '1', '37', '132456', '2022-10-08 14:45:22', '132456', '2022-10-08 14:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2068', '3', '1', '132456', '2022-10-08 14:45:22', '132456', '2022-10-08 14:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2068', '4', '0', '132456', '2022-10-08 14:45:22', '132456', '2022-10-08 14:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2068', '6', 'Data Science Challenge #2068', '132456', '2022-10-08 14:45:22', '132456', '2022-10-08 14:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2068', '7', '1.0', '132456', '2022-10-08 14:45:22', '132456', '2022-10-08 14:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2068', '9', 'On', '132456', '2022-10-08 14:45:22', '132456', '2022-10-08 14:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2068', '10', 'On', '132456', '2022-10-08 14:45:22', '132456', '2022-10-08 14:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2068', '11', 'On', '132456', '2022-10-08 14:45:22', '132456', '2022-10-08 14:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2068', '12', 'Yes', '132456', '2022-10-08 14:45:22', '132456', '2022-10-08 14:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2068', '13', 'Yes', '132456', '2022-10-08 14:45:22', '132456', '2022-10-08 14:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2068', '14', 'Open', '132456', '2022-10-08 14:45:22', '132456', '2022-10-08 14:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2068', '16', '0.0', '132456', '2022-10-08 14:45:22', '132456', '2022-10-08 14:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2068', '26', 'On', '132456', '2022-10-08 14:45:22', '132456', '2022-10-08 14:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2068', '29', 'On', '132456', '2022-10-08 14:45:22', '132456', '2022-10-08 14:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2068', '42', 'true', '132456', '2022-10-08 14:45:22', '132456', '2022-10-08 14:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2068', '43', 'true', '132456', '2022-10-08 14:45:22', '132456', '2022-10-08 14:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20681', '2068', '1', '3', '2022-10-08 14:45:22', '2022-10-08 14:45:22', '2022-10-10 14:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-08 14:45:22', '132456', '2022-10-08 14:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20682', '2068', '2', '3', '2022-10-08 14:45:22', '2022-10-08 14:45:22', '2022-10-10 14:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-08 14:45:22', '132456', '2022-10-08 14:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20684', '2068', '4', '2', '2022-10-10 14:45:22', '2022-10-10 14:45:22', '2022-10-11 14:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-08 14:45:22', '132456', '2022-10-08 14:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20681', '2', '0', '132456', '2022-10-08 14:45:22', '132456', '2022-10-08 14:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20681', '5', 'No', '132456', '2022-10-08 14:45:22', '132456', '2022-10-08 14:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20682', '3', '0', '132456', '2022-10-08 14:45:22', '132456', '2022-10-08 14:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20682', '5', 'No', '132456', '2022-10-08 14:45:22', '132456', '2022-10-08 14:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20684', '5', 'No', '132456', '2022-10-08 14:45:22', '132456', '2022-10-08 14:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20684', '1', '30000413', '132456', '2022-10-08 14:45:22', '132456', '2022-10-08 14:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20681', '20682', '1', '1', '0', '132456', '2022-10-08 14:45:22', '132456', '2022-10-08 14:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20682', '20684', '0', '1', '0', '132456', '2022-10-08 14:45:22', '132456', '2022-10-08 14:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2068', '13', '2068', NULL, 132456, '132456', '2022-10-08 14:45:22', '132456', '2022-10-08 14:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2068', '1', '132456', '132456', '2022-10-08 14:45:22', '132456', '2022-10-08 14:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2068', '2', 'dok', '132456', '2022-10-08 14:45:22', '132456', '2022-10-08 14:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2069', '1', '9', '132456', '2022-10-09 07:45:22', '132456', '2022-10-09 07:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2069', '3', '1', '132456', '2022-10-09 07:45:22', '132456', '2022-10-09 07:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2069', '4', '0', '132456', '2022-10-09 07:45:22', '132456', '2022-10-09 07:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2069', '6', 'QA Challenge #2069', '132456', '2022-10-09 07:45:22', '132456', '2022-10-09 07:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2069', '7', '1.0', '132456', '2022-10-09 07:45:22', '132456', '2022-10-09 07:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2069', '9', 'On', '132456', '2022-10-09 07:45:22', '132456', '2022-10-09 07:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2069', '10', 'On', '132456', '2022-10-09 07:45:22', '132456', '2022-10-09 07:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2069', '11', 'On', '132456', '2022-10-09 07:45:22', '132456', '2022-10-09 07:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2069', '12', 'Yes', '132456', '2022-10-09 07:45:22', '132456', '2022-10-09 07:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2069', '13', 'Yes', '132456', '2022-10-09 07:45:22', '132456', '2022-10-09 07:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2069', '14', 'Open', '132456', '2022-10-09 07:45:22', '132456', '2022-10-09 07:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2069', '16', '0.0', '132456', '2022-10-09 07:45:22', '132456', '2022-10-09 07:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2069', '26', 'On', '132456', '2022-10-09 07:45:22', '132456', '2022-10-09 07:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2069', '29', 'On', '132456', '2022-10-09 07:45:22', '132456', '2022-10-09 07:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2069', '42', 'true', '132456', '2022-10-09 07:45:22', '132456', '2022-10-09 07:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2069', '43', 'true', '132456', '2022-10-09 07:45:22', '132456', '2022-10-09 07:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20691', '2069', '1', '2', '2022-10-09 07:45:22', '2022-10-09 07:45:22', '2022-10-11 07:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-09 07:45:22', '132456', '2022-10-09 07:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20692', '2069', '2', '2', '2022-10-09 07:45:22', '2022-10-09 07:45:22', '2022-10-11 07:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-09 07:45:22', '132456', '2022-10-09 07:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20694', '2069', '4', '1', '2022-10-11 07:45:22', '2022-10-11 07:45:22', '2022-10-12 07:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-09 07:45:22', '132456', '2022-10-09 07:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20691', '2', '0', '132456', '2022-10-09 07:45:22', '132456', '2022-10-09 07:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20691', '5', 'No', '132456', '2022-10-09 07:45:22', '132456', '2022-10-09 07:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20692', '3', '0', '132456', '2022-10-09 07:45:22', '132456', '2022-10-09 07:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20692', '5', 'No', '132456', '2022-10-09 07:45:22', '132456', '2022-10-09 07:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20694', '5', 'No', '132456', '2022-10-09 07:45:22', '132456', '2022-10-09 07:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20694', '1', '30000413', '132456', '2022-10-09 07:45:22', '132456', '2022-10-09 07:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20691', '20692', '1', '1', '0', '132456', '2022-10-09 07:45:22', '132456', '2022-10-09 07:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20692', '20694', '0', '1', '0', '132456', '2022-10-09 07:45:22', '132456', '2022-10-09 07:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2069', '13', '2069', NULL, 132456, '132456', '2022-10-09 07:45:22', '132456', '2022-10-09 07:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2069', '1', '132456', '132456', '2022-10-09 07:45:22', '132456', '2022-10-09 07:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2069', '2', 'dok', '132456', '2022-10-09 07:45:22', '132456', '2022-10-09 07:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2070', '1', '17', '132456', '2022-10-07 23:45:22', '132456', '2022-10-07 23:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2070', '3', '1', '132456', '2022-10-07 23:45:22', '132456', '2022-10-07 23:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2070', '4', '0', '132456', '2022-10-07 23:45:22', '132456', '2022-10-07 23:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2070', '6', 'Design 2 Round Challenge #2070', '132456', '2022-10-07 23:45:22', '132456', '2022-10-07 23:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2070', '7', '1.0', '132456', '2022-10-07 23:45:22', '132456', '2022-10-07 23:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2070', '9', 'On', '132456', '2022-10-07 23:45:22', '132456', '2022-10-07 23:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2070', '10', 'On', '132456', '2022-10-07 23:45:22', '132456', '2022-10-07 23:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2070', '11', 'On', '132456', '2022-10-07 23:45:22', '132456', '2022-10-07 23:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2070', '12', 'Yes', '132456', '2022-10-07 23:45:22', '132456', '2022-10-07 23:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2070', '13', 'Yes', '132456', '2022-10-07 23:45:22', '132456', '2022-10-07 23:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2070', '14', 'Open', '132456', '2022-10-07 23:45:22', '132456', '2022-10-07 23:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2070', '16', '0.0', '132456', '2022-10-07 23:45:22', '132456', '2022-10-07 23:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2070', '26', 'On', '132456', '2022-10-07 23:45:22', '132456', '2022-10-07 23:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2070', '29', 'On', '132456', '2022-10-07 23:45:22', '132456', '2022-10-07 23:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2070', '42', 'true', '132456', '2022-10-07 23:45:22', '132456', '2022-10-07 23:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2070', '43', 'true', '132456', '2022-10-07 23:45:22', '132456', '2022-10-07 23:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('207013', '2070', '13', '3', '2022-10-07 23:45:22', '2022-10-07 23:45:22', '2022-10-07 23:45:22', NULL, NULL, '0.0', '132456', '2022-10-07 23:45:22', '132456', '2022-10-07 23:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('207014', '2070', '14', '3', '2022-10-07 23:45:22', '2022-10-07 23:45:22', '2022-10-08 00:45:22', NULL, NULL, '3600000.0', '132456', '2022-10-07 23:45:22', '132456', '2022-10-07 23:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20701', '2070', '1', '3', '2022-10-08 00:45:22', '2022-10-08 00:45:22', '2022-10-10 00:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-07 23:45:22', '132456', '2022-10-07 23:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('207015', '2070', '15', '3', '2022-10-08 00:45:22', '2022-10-08 00:45:22', '2022-10-10 00:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-07 23:45:22', '132456', '2022-10-07 23:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('207016', '2070', '16', '3', '2022-10-10 00:45:22', '2022-10-10 00:45:22', '2022-10-10 06:45:22', NULL, NULL, '21600000.0', '132456', '2022-10-07 23:45:22', '132456', '2022-10-07 23:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('207017', '2070', '17', '2', '2022-10-10 06:45:22', '2022-10-10 06:45:22', '2022-10-11 06:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-07 23:45:22', '132456', '2022-10-07 23:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20702', '2070', '2', '2', '2022-10-08 00:45:22', '2022-10-08 00:45:22', '2022-10-12 00:45:22', NULL, NULL, '345600000.0', '132456', '2022-10-07 23:45:22', '132456', '2022-10-07 23:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20703', '2070', '3', '1', '2022-10-12 00:45:22', '2022-10-12 00:45:22', '2022-10-12 12:45:22', NULL, NULL, '43200000.0', '132456', '2022-10-07 23:45:22', '132456', '2022-10-07 23:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20704', '2070', '4', '1', '2022-10-12 12:45:22', '2022-10-12 12:45:22', '2022-10-13 00:45:22', NULL, NULL, '43200000.0', '132456', '2022-10-07 23:45:22', '132456', '2022-10-07 23:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('207011', '2070', '11', '1', '2022-10-13 00:45:22', '2022-10-13 00:45:22', '2022-10-13 12:45:22', NULL, NULL, '43200000.0', '132456', '2022-10-07 23:45:22', '132456', '2022-10-07 23:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20709', '2070', '9', '1', '2022-10-13 12:45:22', '2022-10-13 12:45:22', '2022-10-14 12:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-07 23:45:22', '132456', '2022-10-07 23:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('207010', '2070', '10', '1', '2022-10-14 12:45:22', '2022-10-14 12:45:22', '2022-10-14 18:45:22', NULL, NULL, '21600000.0', '132456', '2022-10-07 23:45:22', '132456', '2022-10-07 23:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('207013', '3', '0', '132456', '2022-10-07 23:45:22', '132456', '2022-10-07 23:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('207013', '5', 'No', '132456', '2022-10-07 23:45:22', '132456', '2022-10-07 23:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('207014', '1', '30000722', '132456', '2022-10-07 23:45:22', '132456', '2022-10-07 23:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('207014', '6', '3', '132456', '2022-10-07 23:45:22', '132456', '2022-10-07 23:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20701', '2', '0', '132456', '2022-10-07 23:45:22', '132456', '2022-10-07 23:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20701', '5', 'No', '132456', '2022-10-07 23:45:22', '132456', '2022-10-07 23:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('207015', '3', '0', '132456', '2022-10-07 23:45:22', '132456', '2022-10-07 23:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('207015', '5', 'No', '132456', '2022-10-07 23:45:22', '132456', '2022-10-07 23:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('207016', '1', '30000416', '132456', '2022-10-07 23:45:22', '132456', '2022-10-07 23:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('207017', '1', '30000417', '132456', '2022-10-07 23:45:22', '132456', '2022-10-07 23:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('207017', '6', '3', '132456', '2022-10-07 23:45:22', '132456', '2022-10-07 23:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20702', '3', '0', '132456', '2022-10-07 23:45:22', '132456', '2022-10-07 23:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20702', '5', 'No', '132456', '2022-10-07 23:45:22', '132456', '2022-10-07 23:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20703', '1', '30000410', '132456', '2022-10-07 23:45:22', '132456', '2022-10-07 23:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20704', '5', 'No', '132456', '2022-10-07 23:45:22', '132456', '2022-10-07 23:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20704', '1', '30000411', '132456', '2022-10-07 23:45:22', '132456', '2022-10-07 23:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20704', '6', '3', '132456', '2022-10-07 23:45:22', '132456', '2022-10-07 23:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('207011', '1', '30000720', '132456', '2022-10-07 23:45:22', '132456', '2022-10-07 23:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20709', '5', 'No', '132456', '2022-10-07 23:45:22', '132456', '2022-10-07 23:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('207010', '5', 'No', '132456', '2022-10-07 23:45:22', '132456', '2022-10-07 23:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('207013', '207014', '1', '1', '0', '132456', '2022-10-07 23:45:22', '132456', '2022-10-07 23:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('207014', '20701', '0', '1', '0', '132456', '2022-10-07 23:45:22', '132456', '2022-10-07 23:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20701', '207015', '0', '1', '0', '132456', '2022-10-07 23:45:22', '132456', '2022-10-07 23:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('207015', '207016', '0', '1', '0', '132456', '2022-10-07 23:45:22', '132456', '2022-10-07 23:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('207016', '207017', '0', '1', '0', '132456', '2022-10-07 23:45:22', '132456', '2022-10-07 23:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('207017', '20702', '0', '1', '0', '132456', '2022-10-07 23:45:22', '132456', '2022-10-07 23:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20702', '20703', '0', '1', '0', '132456', '2022-10-07 23:45:22', '132456', '2022-10-07 23:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20703', '20704', '0', '1', '0', '132456', '2022-10-07 23:45:22', '132456', '2022-10-07 23:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20704', '207011', '0', '1', '0', '132456', '2022-10-07 23:45:22', '132456', '2022-10-07 23:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('207011', '20709', '0', '1', '0', '132456', '2022-10-07 23:45:22', '132456', '2022-10-07 23:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20709', '207010', '0', '1', '0', '132456', '2022-10-07 23:45:22', '132456', '2022-10-07 23:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2070', '13', '2070', NULL, 132456, '132456', '2022-10-07 23:45:22', '132456', '2022-10-07 23:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2070', '1', '132456', '132456', '2022-10-07 23:45:22', '132456', '2022-10-07 23:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2070', '2', 'dok', '132456', '2022-10-07 23:45:22', '132456', '2022-10-07 23:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2071', '1', '38', '132456', '2022-10-08 08:45:22', '132456', '2022-10-08 08:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2071', '3', '1', '132456', '2022-10-08 08:45:22', '132456', '2022-10-08 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2071', '4', '0', '132456', '2022-10-08 08:45:22', '132456', '2022-10-08 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2071', '6', 'First2Finish Challenge #2071', '132456', '2022-10-08 08:45:22', '132456', '2022-10-08 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2071', '7', '1.0', '132456', '2022-10-08 08:45:22', '132456', '2022-10-08 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2071', '9', 'On', '132456', '2022-10-08 08:45:22', '132456', '2022-10-08 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2071', '10', 'On', '132456', '2022-10-08 08:45:22', '132456', '2022-10-08 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2071', '11', 'On', '132456', '2022-10-08 08:45:22', '132456', '2022-10-08 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2071', '12', 'Yes', '132456', '2022-10-08 08:45:22', '132456', '2022-10-08 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2071', '13', 'Yes', '132456', '2022-10-08 08:45:22', '132456', '2022-10-08 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2071', '14', 'Open', '132456', '2022-10-08 08:45:22', '132456', '2022-10-08 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2071', '16', '0.0', '132456', '2022-10-08 08:45:22', '132456', '2022-10-08 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2071', '26', 'On', '132456', '2022-10-08 08:45:22', '132456', '2022-10-08 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2071', '29', 'On', '132456', '2022-10-08 08:45:22', '132456', '2022-10-08 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2071', '42', 'true', '132456', '2022-10-08 08:45:22', '132456', '2022-10-08 08:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2071', '43', 'true', '132456', '2022-10-08 08:45:22', '132456', '2022-10-08 08:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20711', '2071', '1', '2', '2022-10-08 08:45:22', '2022-10-08 08:45:22', '2022-11-07 08:45:22', NULL, NULL, '2592000000.0', '132456', '2022-10-08 08:45:22', '132456', '2022-10-08 08:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20712', '2071', '2', '2', '2022-10-08 08:45:22', '2022-10-08 08:45:22', '2022-11-07 08:45:22', NULL, NULL, '2592000000.0', '132456', '2022-10-08 08:45:22', '132456', '2022-10-08 08:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('207118', '2071', '18', '3', '2022-10-09 08:45:22', '2022-10-09 08:45:22', '2022-10-10 08:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-08 08:45:22', '132456', '2022-10-08 08:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20711', '2', '0', '132456', '2022-10-08 08:45:22', '132456', '2022-10-08 08:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20711', '5', 'No', '132456', '2022-10-08 08:45:22', '132456', '2022-10-08 08:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20712', '3', '0', '132456', '2022-10-08 08:45:22', '132456', '2022-10-08 08:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20712', '5', 'No', '132456', '2022-10-08 08:45:22', '132456', '2022-10-08 08:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('207118', '1', '30000419', '132456', '2022-10-08 08:45:22', '132456', '2022-10-08 08:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20711', '20712', '1', '1', '0', '132456', '2022-10-08 08:45:22', '132456', '2022-10-08 08:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20712', '207118', '0', '1', '0', '132456', '2022-10-08 08:45:22', '132456', '2022-10-08 08:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2071', '13', '2071', NULL, 132456, '132456', '2022-10-08 08:45:22', '132456', '2022-10-08 08:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2071', '1', '132456', '132456', '2022-10-08 08:45:22', '132456', '2022-10-08 08:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2071', '2', 'dok', '132456', '2022-10-08 08:45:22', '132456', '2022-10-08 08:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2072', '1', '9', '132456', '2022-10-04 01:45:22', '132456', '2022-10-04 01:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2072', '3', '1', '132456', '2022-10-04 01:45:22', '132456', '2022-10-04 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2072', '4', '0', '132456', '2022-10-04 01:45:22', '132456', '2022-10-04 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2072', '6', 'QA Challenge #2072', '132456', '2022-10-04 01:45:22', '132456', '2022-10-04 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2072', '7', '1.0', '132456', '2022-10-04 01:45:22', '132456', '2022-10-04 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2072', '9', 'On', '132456', '2022-10-04 01:45:22', '132456', '2022-10-04 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2072', '10', 'On', '132456', '2022-10-04 01:45:22', '132456', '2022-10-04 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2072', '11', 'On', '132456', '2022-10-04 01:45:22', '132456', '2022-10-04 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2072', '12', 'Yes', '132456', '2022-10-04 01:45:22', '132456', '2022-10-04 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2072', '13', 'Yes', '132456', '2022-10-04 01:45:22', '132456', '2022-10-04 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2072', '14', 'Open', '132456', '2022-10-04 01:45:22', '132456', '2022-10-04 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2072', '16', '0.0', '132456', '2022-10-04 01:45:22', '132456', '2022-10-04 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2072', '26', 'On', '132456', '2022-10-04 01:45:22', '132456', '2022-10-04 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2072', '29', 'On', '132456', '2022-10-04 01:45:22', '132456', '2022-10-04 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2072', '42', 'true', '132456', '2022-10-04 01:45:22', '132456', '2022-10-04 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2072', '43', 'true', '132456', '2022-10-04 01:45:22', '132456', '2022-10-04 01:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20721', '2072', '1', '3', '2022-10-04 01:45:22', '2022-10-04 01:45:22', '2022-10-06 01:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-04 01:45:22', '132456', '2022-10-04 01:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20722', '2072', '2', '3', '2022-10-04 01:45:22', '2022-10-04 01:45:22', '2022-10-06 01:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-04 01:45:22', '132456', '2022-10-04 01:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20724', '2072', '4', '3', '2022-10-06 01:45:22', '2022-10-06 01:45:22', '2022-10-07 01:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-04 01:45:22', '132456', '2022-10-04 01:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20721', '2', '0', '132456', '2022-10-04 01:45:22', '132456', '2022-10-04 01:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20721', '5', 'No', '132456', '2022-10-04 01:45:22', '132456', '2022-10-04 01:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20722', '3', '0', '132456', '2022-10-04 01:45:22', '132456', '2022-10-04 01:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20722', '5', 'No', '132456', '2022-10-04 01:45:22', '132456', '2022-10-04 01:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20724', '5', 'No', '132456', '2022-10-04 01:45:22', '132456', '2022-10-04 01:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20724', '1', '30000413', '132456', '2022-10-04 01:45:22', '132456', '2022-10-04 01:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20721', '20722', '1', '1', '0', '132456', '2022-10-04 01:45:22', '132456', '2022-10-04 01:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20722', '20724', '0', '1', '0', '132456', '2022-10-04 01:45:22', '132456', '2022-10-04 01:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2072', '13', '2072', NULL, 132456, '132456', '2022-10-04 01:45:22', '132456', '2022-10-04 01:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2072', '1', '132456', '132456', '2022-10-04 01:45:22', '132456', '2022-10-04 01:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2072', '2', 'dok', '132456', '2022-10-04 01:45:22', '132456', '2022-10-04 01:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2073', '1', '37', '132456', '2022-10-08 11:45:22', '132456', '2022-10-08 11:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2073', '3', '1', '132456', '2022-10-08 11:45:22', '132456', '2022-10-08 11:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2073', '4', '0', '132456', '2022-10-08 11:45:22', '132456', '2022-10-08 11:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2073', '6', 'Data Science Challenge #2073', '132456', '2022-10-08 11:45:22', '132456', '2022-10-08 11:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2073', '7', '1.0', '132456', '2022-10-08 11:45:22', '132456', '2022-10-08 11:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2073', '9', 'On', '132456', '2022-10-08 11:45:22', '132456', '2022-10-08 11:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2073', '10', 'On', '132456', '2022-10-08 11:45:22', '132456', '2022-10-08 11:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2073', '11', 'On', '132456', '2022-10-08 11:45:22', '132456', '2022-10-08 11:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2073', '12', 'Yes', '132456', '2022-10-08 11:45:22', '132456', '2022-10-08 11:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2073', '13', 'Yes', '132456', '2022-10-08 11:45:22', '132456', '2022-10-08 11:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2073', '14', 'Open', '132456', '2022-10-08 11:45:22', '132456', '2022-10-08 11:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2073', '16', '0.0', '132456', '2022-10-08 11:45:22', '132456', '2022-10-08 11:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2073', '26', 'On', '132456', '2022-10-08 11:45:22', '132456', '2022-10-08 11:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2073', '29', 'On', '132456', '2022-10-08 11:45:22', '132456', '2022-10-08 11:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2073', '42', 'true', '132456', '2022-10-08 11:45:22', '132456', '2022-10-08 11:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2073', '43', 'true', '132456', '2022-10-08 11:45:22', '132456', '2022-10-08 11:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20731', '2073', '1', '3', '2022-10-08 11:45:22', '2022-10-08 11:45:22', '2022-10-10 11:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-08 11:45:22', '132456', '2022-10-08 11:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20732', '2073', '2', '3', '2022-10-08 11:45:22', '2022-10-08 11:45:22', '2022-10-10 11:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-08 11:45:22', '132456', '2022-10-08 11:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20734', '2073', '4', '2', '2022-10-10 11:45:22', '2022-10-10 11:45:22', '2022-10-11 11:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-08 11:45:22', '132456', '2022-10-08 11:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20731', '2', '0', '132456', '2022-10-08 11:45:22', '132456', '2022-10-08 11:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20731', '5', 'No', '132456', '2022-10-08 11:45:22', '132456', '2022-10-08 11:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20732', '3', '0', '132456', '2022-10-08 11:45:22', '132456', '2022-10-08 11:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20732', '5', 'No', '132456', '2022-10-08 11:45:22', '132456', '2022-10-08 11:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20734', '5', 'No', '132456', '2022-10-08 11:45:22', '132456', '2022-10-08 11:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20734', '1', '30000413', '132456', '2022-10-08 11:45:22', '132456', '2022-10-08 11:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20731', '20732', '1', '1', '0', '132456', '2022-10-08 11:45:22', '132456', '2022-10-08 11:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20732', '20734', '0', '1', '0', '132456', '2022-10-08 11:45:22', '132456', '2022-10-08 11:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2073', '13', '2073', NULL, 132456, '132456', '2022-10-08 11:45:22', '132456', '2022-10-08 11:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2073', '1', '132456', '132456', '2022-10-08 11:45:22', '132456', '2022-10-08 11:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2073', '2', 'dok', '132456', '2022-10-08 11:45:22', '132456', '2022-10-08 11:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2074', '1', '39', '132456', '2022-10-07 20:45:22', '132456', '2022-10-07 20:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2074', '3', '1', '132456', '2022-10-07 20:45:22', '132456', '2022-10-07 20:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2074', '4', '0', '132456', '2022-10-07 20:45:22', '132456', '2022-10-07 20:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2074', '6', 'Development Challenge #2074', '132456', '2022-10-07 20:45:22', '132456', '2022-10-07 20:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2074', '7', '1.0', '132456', '2022-10-07 20:45:22', '132456', '2022-10-07 20:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2074', '9', 'On', '132456', '2022-10-07 20:45:22', '132456', '2022-10-07 20:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2074', '10', 'On', '132456', '2022-10-07 20:45:22', '132456', '2022-10-07 20:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2074', '11', 'On', '132456', '2022-10-07 20:45:22', '132456', '2022-10-07 20:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2074', '12', 'Yes', '132456', '2022-10-07 20:45:22', '132456', '2022-10-07 20:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2074', '13', 'Yes', '132456', '2022-10-07 20:45:22', '132456', '2022-10-07 20:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2074', '14', 'Open', '132456', '2022-10-07 20:45:22', '132456', '2022-10-07 20:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2074', '16', '0.0', '132456', '2022-10-07 20:45:22', '132456', '2022-10-07 20:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2074', '26', 'On', '132456', '2022-10-07 20:45:22', '132456', '2022-10-07 20:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2074', '29', 'On', '132456', '2022-10-07 20:45:22', '132456', '2022-10-07 20:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2074', '42', 'true', '132456', '2022-10-07 20:45:22', '132456', '2022-10-07 20:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2074', '43', 'true', '132456', '2022-10-07 20:45:22', '132456', '2022-10-07 20:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20741', '2074', '1', '3', '2022-10-07 20:45:22', '2022-10-07 20:45:22', '2022-10-09 20:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-07 20:45:22', '132456', '2022-10-07 20:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20742', '2074', '2', '3', '2022-10-07 20:45:22', '2022-10-07 20:45:22', '2022-10-09 20:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-07 20:45:22', '132456', '2022-10-07 20:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20744', '2074', '4', '3', '2022-10-09 20:45:22', '2022-10-09 20:45:22', '2022-10-10 20:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-07 20:45:22', '132456', '2022-10-07 20:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20745', '2074', '5', '2', '2022-10-10 20:45:22', '2022-10-10 20:45:22', '2022-10-11 20:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-07 20:45:22', '132456', '2022-10-07 20:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20746', '2074', '6', '1', '2022-10-11 20:45:22', '2022-10-11 20:45:22', '2022-10-12 08:45:22', NULL, NULL, '43200000.0', '132456', '2022-10-07 20:45:22', '132456', '2022-10-07 20:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20741', '2', '0', '132456', '2022-10-07 20:45:22', '132456', '2022-10-07 20:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20741', '5', 'No', '132456', '2022-10-07 20:45:22', '132456', '2022-10-07 20:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20742', '3', '0', '132456', '2022-10-07 20:45:22', '132456', '2022-10-07 20:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20742', '5', 'No', '132456', '2022-10-07 20:45:22', '132456', '2022-10-07 20:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20744', '5', 'No', '132456', '2022-10-07 20:45:22', '132456', '2022-10-07 20:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20744', '1', '30000413', '132456', '2022-10-07 20:45:22', '132456', '2022-10-07 20:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20745', '5', 'No', '132456', '2022-10-07 20:45:22', '132456', '2022-10-07 20:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20745', '4', 'No', '132456', '2022-10-07 20:45:22', '132456', '2022-10-07 20:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20746', '5', 'No', '132456', '2022-10-07 20:45:22', '132456', '2022-10-07 20:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20741', '20742', '1', '1', '0', '132456', '2022-10-07 20:45:22', '132456', '2022-10-07 20:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20742', '20744', '0', '1', '0', '132456', '2022-10-07 20:45:22', '132456', '2022-10-07 20:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20744', '20745', '0', '1', '0', '132456', '2022-10-07 20:45:22', '132456', '2022-10-07 20:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20745', '20746', '0', '1', '0', '132456', '2022-10-07 20:45:22', '132456', '2022-10-07 20:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2074', '13', '2074', NULL, 132456, '132456', '2022-10-07 20:45:22', '132456', '2022-10-07 20:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2074', '1', '132456', '132456', '2022-10-07 20:45:22', '132456', '2022-10-07 20:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2074', '2', 'dok', '132456', '2022-10-07 20:45:22', '132456', '2022-10-07 20:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2075', '1', '9', '132456', '2022-10-08 20:45:22', '132456', '2022-10-08 20:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2075', '3', '1', '132456', '2022-10-08 20:45:22', '132456', '2022-10-08 20:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2075', '4', '0', '132456', '2022-10-08 20:45:22', '132456', '2022-10-08 20:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2075', '6', 'QA Challenge #2075', '132456', '2022-10-08 20:45:22', '132456', '2022-10-08 20:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2075', '7', '1.0', '132456', '2022-10-08 20:45:22', '132456', '2022-10-08 20:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2075', '9', 'On', '132456', '2022-10-08 20:45:22', '132456', '2022-10-08 20:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2075', '10', 'On', '132456', '2022-10-08 20:45:22', '132456', '2022-10-08 20:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2075', '11', 'On', '132456', '2022-10-08 20:45:22', '132456', '2022-10-08 20:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2075', '12', 'Yes', '132456', '2022-10-08 20:45:22', '132456', '2022-10-08 20:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2075', '13', 'Yes', '132456', '2022-10-08 20:45:22', '132456', '2022-10-08 20:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2075', '14', 'Open', '132456', '2022-10-08 20:45:22', '132456', '2022-10-08 20:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2075', '16', '0.0', '132456', '2022-10-08 20:45:22', '132456', '2022-10-08 20:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2075', '26', 'On', '132456', '2022-10-08 20:45:22', '132456', '2022-10-08 20:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2075', '29', 'On', '132456', '2022-10-08 20:45:22', '132456', '2022-10-08 20:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2075', '42', 'true', '132456', '2022-10-08 20:45:22', '132456', '2022-10-08 20:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2075', '43', 'true', '132456', '2022-10-08 20:45:22', '132456', '2022-10-08 20:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20751', '2075', '1', '3', '2022-10-08 20:45:22', '2022-10-08 20:45:22', '2022-10-10 20:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-08 20:45:22', '132456', '2022-10-08 20:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20752', '2075', '2', '3', '2022-10-08 20:45:22', '2022-10-08 20:45:22', '2022-10-10 20:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-08 20:45:22', '132456', '2022-10-08 20:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20754', '2075', '4', '2', '2022-10-10 20:45:22', '2022-10-10 20:45:22', '2022-10-11 20:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-08 20:45:22', '132456', '2022-10-08 20:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20751', '2', '0', '132456', '2022-10-08 20:45:22', '132456', '2022-10-08 20:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20751', '5', 'No', '132456', '2022-10-08 20:45:22', '132456', '2022-10-08 20:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20752', '3', '0', '132456', '2022-10-08 20:45:22', '132456', '2022-10-08 20:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20752', '5', 'No', '132456', '2022-10-08 20:45:22', '132456', '2022-10-08 20:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20754', '5', 'No', '132456', '2022-10-08 20:45:22', '132456', '2022-10-08 20:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20754', '1', '30000413', '132456', '2022-10-08 20:45:22', '132456', '2022-10-08 20:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20751', '20752', '1', '1', '0', '132456', '2022-10-08 20:45:22', '132456', '2022-10-08 20:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20752', '20754', '0', '1', '0', '132456', '2022-10-08 20:45:22', '132456', '2022-10-08 20:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2075', '13', '2075', NULL, 132456, '132456', '2022-10-08 20:45:22', '132456', '2022-10-08 20:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2075', '1', '132456', '132456', '2022-10-08 20:45:22', '132456', '2022-10-08 20:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2075', '2', 'dok', '132456', '2022-10-08 20:45:22', '132456', '2022-10-08 20:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2076', '1', '17', '132456', '2022-10-10 05:45:22', '132456', '2022-10-10 05:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2076', '3', '1', '132456', '2022-10-10 05:45:22', '132456', '2022-10-10 05:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2076', '4', '0', '132456', '2022-10-10 05:45:22', '132456', '2022-10-10 05:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2076', '6', 'Design 2 Round Challenge #2076', '132456', '2022-10-10 05:45:22', '132456', '2022-10-10 05:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2076', '7', '1.0', '132456', '2022-10-10 05:45:22', '132456', '2022-10-10 05:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2076', '9', 'On', '132456', '2022-10-10 05:45:22', '132456', '2022-10-10 05:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2076', '10', 'On', '132456', '2022-10-10 05:45:22', '132456', '2022-10-10 05:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2076', '11', 'On', '132456', '2022-10-10 05:45:22', '132456', '2022-10-10 05:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2076', '12', 'Yes', '132456', '2022-10-10 05:45:22', '132456', '2022-10-10 05:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2076', '13', 'Yes', '132456', '2022-10-10 05:45:22', '132456', '2022-10-10 05:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2076', '14', 'Open', '132456', '2022-10-10 05:45:22', '132456', '2022-10-10 05:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2076', '16', '0.0', '132456', '2022-10-10 05:45:22', '132456', '2022-10-10 05:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2076', '26', 'On', '132456', '2022-10-10 05:45:22', '132456', '2022-10-10 05:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2076', '29', 'On', '132456', '2022-10-10 05:45:22', '132456', '2022-10-10 05:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2076', '42', 'true', '132456', '2022-10-10 05:45:22', '132456', '2022-10-10 05:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2076', '43', 'true', '132456', '2022-10-10 05:45:22', '132456', '2022-10-10 05:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('207613', '2076', '13', '3', '2022-10-10 05:45:22', '2022-10-10 05:45:22', '2022-10-10 05:45:22', NULL, NULL, '0.0', '132456', '2022-10-10 05:45:22', '132456', '2022-10-10 05:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('207614', '2076', '14', '3', '2022-10-10 05:45:22', '2022-10-10 05:45:22', '2022-10-10 06:45:22', NULL, NULL, '3600000.0', '132456', '2022-10-10 05:45:22', '132456', '2022-10-10 05:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20761', '2076', '1', '2', '2022-10-10 06:45:22', '2022-10-10 06:45:22', '2022-10-12 06:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-10 05:45:22', '132456', '2022-10-10 05:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('207615', '2076', '15', '2', '2022-10-10 06:45:22', '2022-10-10 06:45:22', '2022-10-12 06:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-10 05:45:22', '132456', '2022-10-10 05:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('207616', '2076', '16', '1', '2022-10-12 06:45:22', '2022-10-12 06:45:22', '2022-10-12 12:45:22', NULL, NULL, '21600000.0', '132456', '2022-10-10 05:45:22', '132456', '2022-10-10 05:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('207617', '2076', '17', '1', '2022-10-12 12:45:22', '2022-10-12 12:45:22', '2022-10-13 12:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-10 05:45:22', '132456', '2022-10-10 05:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20762', '2076', '2', '2', '2022-10-10 06:45:22', '2022-10-10 06:45:22', '2022-10-14 06:45:22', NULL, NULL, '345600000.0', '132456', '2022-10-10 05:45:22', '132456', '2022-10-10 05:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20763', '2076', '3', '1', '2022-10-14 06:45:22', '2022-10-14 06:45:22', '2022-10-14 18:45:22', NULL, NULL, '43200000.0', '132456', '2022-10-10 05:45:22', '132456', '2022-10-10 05:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20764', '2076', '4', '1', '2022-10-14 18:45:22', '2022-10-14 18:45:22', '2022-10-15 06:45:22', NULL, NULL, '43200000.0', '132456', '2022-10-10 05:45:22', '132456', '2022-10-10 05:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('207611', '2076', '11', '1', '2022-10-15 06:45:22', '2022-10-15 06:45:22', '2022-10-15 18:45:22', NULL, NULL, '43200000.0', '132456', '2022-10-10 05:45:22', '132456', '2022-10-10 05:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20769', '2076', '9', '1', '2022-10-15 18:45:22', '2022-10-15 18:45:22', '2022-10-16 18:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-10 05:45:22', '132456', '2022-10-10 05:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('207610', '2076', '10', '1', '2022-10-16 18:45:22', '2022-10-16 18:45:22', '2022-10-17 00:45:22', NULL, NULL, '21600000.0', '132456', '2022-10-10 05:45:22', '132456', '2022-10-10 05:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('207613', '3', '0', '132456', '2022-10-10 05:45:22', '132456', '2022-10-10 05:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('207613', '5', 'No', '132456', '2022-10-10 05:45:22', '132456', '2022-10-10 05:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('207614', '1', '30000722', '132456', '2022-10-10 05:45:22', '132456', '2022-10-10 05:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('207614', '6', '3', '132456', '2022-10-10 05:45:22', '132456', '2022-10-10 05:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20761', '2', '0', '132456', '2022-10-10 05:45:22', '132456', '2022-10-10 05:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20761', '5', 'No', '132456', '2022-10-10 05:45:22', '132456', '2022-10-10 05:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('207615', '3', '0', '132456', '2022-10-10 05:45:22', '132456', '2022-10-10 05:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('207615', '5', 'No', '132456', '2022-10-10 05:45:22', '132456', '2022-10-10 05:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('207616', '1', '30000416', '132456', '2022-10-10 05:45:22', '132456', '2022-10-10 05:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('207617', '1', '30000417', '132456', '2022-10-10 05:45:22', '132456', '2022-10-10 05:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('207617', '6', '3', '132456', '2022-10-10 05:45:22', '132456', '2022-10-10 05:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20762', '3', '0', '132456', '2022-10-10 05:45:22', '132456', '2022-10-10 05:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20762', '5', 'No', '132456', '2022-10-10 05:45:22', '132456', '2022-10-10 05:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20763', '1', '30000410', '132456', '2022-10-10 05:45:22', '132456', '2022-10-10 05:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20764', '5', 'No', '132456', '2022-10-10 05:45:22', '132456', '2022-10-10 05:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20764', '1', '30000411', '132456', '2022-10-10 05:45:22', '132456', '2022-10-10 05:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20764', '6', '3', '132456', '2022-10-10 05:45:22', '132456', '2022-10-10 05:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('207611', '1', '30000720', '132456', '2022-10-10 05:45:22', '132456', '2022-10-10 05:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20769', '5', 'No', '132456', '2022-10-10 05:45:22', '132456', '2022-10-10 05:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('207610', '5', 'No', '132456', '2022-10-10 05:45:22', '132456', '2022-10-10 05:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('207613', '207614', '1', '1', '0', '132456', '2022-10-10 05:45:22', '132456', '2022-10-10 05:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('207614', '20761', '0', '1', '0', '132456', '2022-10-10 05:45:22', '132456', '2022-10-10 05:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20761', '207615', '0', '1', '0', '132456', '2022-10-10 05:45:22', '132456', '2022-10-10 05:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('207615', '207616', '0', '1', '0', '132456', '2022-10-10 05:45:22', '132456', '2022-10-10 05:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('207616', '207617', '0', '1', '0', '132456', '2022-10-10 05:45:22', '132456', '2022-10-10 05:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('207617', '20762', '0', '1', '0', '132456', '2022-10-10 05:45:22', '132456', '2022-10-10 05:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20762', '20763', '0', '1', '0', '132456', '2022-10-10 05:45:22', '132456', '2022-10-10 05:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20763', '20764', '0', '1', '0', '132456', '2022-10-10 05:45:22', '132456', '2022-10-10 05:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20764', '207611', '0', '1', '0', '132456', '2022-10-10 05:45:22', '132456', '2022-10-10 05:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('207611', '20769', '0', '1', '0', '132456', '2022-10-10 05:45:22', '132456', '2022-10-10 05:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20769', '207610', '0', '1', '0', '132456', '2022-10-10 05:45:22', '132456', '2022-10-10 05:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2076', '13', '2076', NULL, 132456, '132456', '2022-10-10 05:45:22', '132456', '2022-10-10 05:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2076', '1', '132456', '132456', '2022-10-10 05:45:22', '132456', '2022-10-10 05:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2076', '2', 'dok', '132456', '2022-10-10 05:45:22', '132456', '2022-10-10 05:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2077', '1', '17', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2077', '3', '1', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2077', '4', '0', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2077', '6', 'Design Challenge #2077', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2077', '7', '1.0', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2077', '9', 'On', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2077', '10', 'On', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2077', '11', 'On', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2077', '12', 'Yes', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2077', '13', 'Yes', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2077', '14', 'Open', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2077', '16', '0.0', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2077', '26', 'On', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2077', '29', 'On', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2077', '42', 'true', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2077', '43', 'true', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20771', '2077', '1', '3', '2022-10-07 10:45:22', '2022-10-07 10:45:22', '2022-10-09 10:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20772', '2077', '2', '3', '2022-10-07 10:45:22', '2022-10-07 10:45:22', '2022-10-09 10:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20773', '2077', '3', '3', '2022-10-09 10:45:22', '2022-10-09 10:45:22', '2022-10-09 22:45:22', NULL, NULL, '43200000.0', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20774', '2077', '4', '3', '2022-10-09 22:45:22', '2022-10-09 22:45:22', '2022-10-10 22:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('207711', '2077', '11', '2', '2022-10-10 22:45:22', '2022-10-10 22:45:22', '2022-10-11 10:45:22', NULL, NULL, '43200000.0', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20771', '2', '0', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20771', '5', 'No', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20772', '3', '0', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20772', '5', 'No', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20773', '1', '30000410', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20774', '5', 'No', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20774', '1', '30000411', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('207711', '1', '30000720', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20771', '20772', '1', '1', '0', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20772', '20773', '0', '1', '0', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20773', '20774', '0', '1', '0', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20774', '207711', '0', '1', '0', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2077', '13', '2077', NULL, 132456, '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2077', '1', '132456', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2077', '2', 'dok', '132456', '2022-10-07 10:45:22', '132456', '2022-10-07 10:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2078', '1', '40', '132456', '2022-10-10 03:45:22', '132456', '2022-10-10 03:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2078', '3', '1', '132456', '2022-10-10 03:45:22', '132456', '2022-10-10 03:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2078', '4', '0', '132456', '2022-10-10 03:45:22', '132456', '2022-10-10 03:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2078', '6', 'First2Finish Design Challenge #2078', '132456', '2022-10-10 03:45:22', '132456', '2022-10-10 03:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2078', '7', '1.0', '132456', '2022-10-10 03:45:22', '132456', '2022-10-10 03:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2078', '9', 'On', '132456', '2022-10-10 03:45:22', '132456', '2022-10-10 03:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2078', '10', 'On', '132456', '2022-10-10 03:45:22', '132456', '2022-10-10 03:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2078', '11', 'On', '132456', '2022-10-10 03:45:22', '132456', '2022-10-10 03:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2078', '12', 'Yes', '132456', '2022-10-10 03:45:22', '132456', '2022-10-10 03:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2078', '13', 'Yes', '132456', '2022-10-10 03:45:22', '132456', '2022-10-10 03:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2078', '14', 'Open', '132456', '2022-10-10 03:45:22', '132456', '2022-10-10 03:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2078', '16', '0.0', '132456', '2022-10-10 03:45:22', '132456', '2022-10-10 03:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2078', '26', 'On', '132456', '2022-10-10 03:45:22', '132456', '2022-10-10 03:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2078', '29', 'On', '132456', '2022-10-10 03:45:22', '132456', '2022-10-10 03:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2078', '42', 'true', '132456', '2022-10-10 03:45:22', '132456', '2022-10-10 03:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2078', '43', 'true', '132456', '2022-10-10 03:45:22', '132456', '2022-10-10 03:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20781', '2078', '1', '2', '2022-10-10 03:45:22', '2022-10-10 03:45:22', '2022-11-09 03:45:22', NULL, NULL, '2592000000.0', '132456', '2022-10-10 03:45:22', '132456', '2022-10-10 03:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20782', '2078', '2', '2', '2022-10-10 03:45:22', '2022-10-10 03:45:22', '2022-11-09 03:45:22', NULL, NULL, '2592000000.0', '132456', '2022-10-10 03:45:22', '132456', '2022-10-10 03:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('207818', '2078', '18', '1', '2022-10-11 03:45:22', '2022-10-11 03:45:22', '2022-10-12 03:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-10 03:45:22', '132456', '2022-10-10 03:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20781', '2', '0', '132456', '2022-10-10 03:45:22', '132456', '2022-10-10 03:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20781', '5', 'No', '132456', '2022-10-10 03:45:22', '132456', '2022-10-10 03:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20782', '3', '0', '132456', '2022-10-10 03:45:22', '132456', '2022-10-10 03:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20782', '5', 'No', '132456', '2022-10-10 03:45:22', '132456', '2022-10-10 03:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('207818', '1', '30000419', '132456', '2022-10-10 03:45:22', '132456', '2022-10-10 03:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20781', '20782', '1', '1', '0', '132456', '2022-10-10 03:45:22', '132456', '2022-10-10 03:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20782', '207818', '0', '1', '0', '132456', '2022-10-10 03:45:22', '132456', '2022-10-10 03:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2078', '13', '2078', NULL, 132456, '132456', '2022-10-10 03:45:22', '132456', '2022-10-10 03:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2078', '1', '132456', '132456', '2022-10-10 03:45:22', '132456', '2022-10-10 03:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2078', '2', 'dok', '132456', '2022-10-10 03:45:22', '132456', '2022-10-10 03:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2079', '1', '38', '132456', '2022-10-04 13:45:22', '132456', '2022-10-04 13:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2079', '3', '1', '132456', '2022-10-04 13:45:22', '132456', '2022-10-04 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2079', '4', '0', '132456', '2022-10-04 13:45:22', '132456', '2022-10-04 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2079', '6', 'First2Finish Challenge #2079', '132456', '2022-10-04 13:45:22', '132456', '2022-10-04 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2079', '7', '1.0', '132456', '2022-10-04 13:45:22', '132456', '2022-10-04 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2079', '9', 'On', '132456', '2022-10-04 13:45:22', '132456', '2022-10-04 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2079', '10', 'On', '132456', '2022-10-04 13:45:22', '132456', '2022-10-04 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2079', '11', 'On', '132456', '2022-10-04 13:45:22', '132456', '2022-10-04 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2079', '12', 'Yes', '132456', '2022-10-04 13:45:22', '132456', '2022-10-04 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2079', '13', 'Yes', '132456', '2022-10-04 13:45:22', '132456', '2022-10-04 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2079', '14', 'Open', '132456', '2022-10-04 13:45:22', '132456', '2022-10-04 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2079', '16', '0.0', '132456', '2022-10-04 13:45:22', '132456', '2022-10-04 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2079', '26', 'On', '132456', '2022-10-04 13:45:22', '132456', '2022-10-04 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2079', '29', 'On', '132456', '2022-10-04 13:45:22', '132456', '2022-10-04 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2079', '42', 'true', '132456', '2022-10-04 13:45:22', '132456', '2022-10-04 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2079', '43', 'true', '132456', '2022-10-04 13:45:22', '132456', '2022-10-04 13:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20791', '2079', '1', '2', '2022-10-04 13:45:22', '2022-10-04 13:45:22', '2022-11-03 13:45:22', NULL, NULL, '2592000000.0', '132456', '2022-10-04 13:45:22', '132456', '2022-10-04 13:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20792', '2079', '2', '2', '2022-10-04 13:45:22', '2022-10-04 13:45:22', '2022-11-03 13:45:22', NULL, NULL, '2592000000.0', '132456', '2022-10-04 13:45:22', '132456', '2022-10-04 13:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('207918', '2079', '18', '3', '2022-10-05 13:45:22', '2022-10-05 13:45:22', '2022-10-06 13:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-04 13:45:22', '132456', '2022-10-04 13:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20791', '2', '0', '132456', '2022-10-04 13:45:22', '132456', '2022-10-04 13:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20791', '5', 'No', '132456', '2022-10-04 13:45:22', '132456', '2022-10-04 13:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20792', '3', '0', '132456', '2022-10-04 13:45:22', '132456', '2022-10-04 13:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20792', '5', 'No', '132456', '2022-10-04 13:45:22', '132456', '2022-10-04 13:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('207918', '1', '30000419', '132456', '2022-10-04 13:45:22', '132456', '2022-10-04 13:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20791', '20792', '1', '1', '0', '132456', '2022-10-04 13:45:22', '132456', '2022-10-04 13:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20792', '207918', '0', '1', '0', '132456', '2022-10-04 13:45:22', '132456', '2022-10-04 13:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2079', '13', '2079', NULL, 132456, '132456', '2022-10-04 13:45:22', '132456', '2022-10-04 13:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2079', '1', '132456', '132456', '2022-10-04 13:45:22', '132456', '2022-10-04 13:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2079', '2', 'dok', '132456', '2022-10-04 13:45:22', '132456', '2022-10-04 13:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2080', '1', '37', '132456', '2022-10-09 18:45:22', '132456', '2022-10-09 18:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2080', '3', '1', '132456', '2022-10-09 18:45:22', '132456', '2022-10-09 18:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2080', '4', '0', '132456', '2022-10-09 18:45:22', '132456', '2022-10-09 18:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2080', '6', 'Data Science Challenge #2080', '132456', '2022-10-09 18:45:22', '132456', '2022-10-09 18:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2080', '7', '1.0', '132456', '2022-10-09 18:45:22', '132456', '2022-10-09 18:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2080', '9', 'On', '132456', '2022-10-09 18:45:22', '132456', '2022-10-09 18:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2080', '10', 'On', '132456', '2022-10-09 18:45:22', '132456', '2022-10-09 18:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2080', '11', 'On', '132456', '2022-10-09 18:45:22', '132456', '2022-10-09 18:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2080', '12', 'Yes', '132456', '2022-10-09 18:45:22', '132456', '2022-10-09 18:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2080', '13', 'Yes', '132456', '2022-10-09 18:45:22', '132456', '2022-10-09 18:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2080', '14', 'Open', '132456', '2022-10-09 18:45:22', '132456', '2022-10-09 18:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2080', '16', '0.0', '132456', '2022-10-09 18:45:22', '132456', '2022-10-09 18:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2080', '26', 'On', '132456', '2022-10-09 18:45:22', '132456', '2022-10-09 18:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2080', '29', 'On', '132456', '2022-10-09 18:45:22', '132456', '2022-10-09 18:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2080', '42', 'true', '132456', '2022-10-09 18:45:22', '132456', '2022-10-09 18:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2080', '43', 'true', '132456', '2022-10-09 18:45:22', '132456', '2022-10-09 18:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20801', '2080', '1', '2', '2022-10-09 18:45:22', '2022-10-09 18:45:22', '2022-10-11 18:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-09 18:45:22', '132456', '2022-10-09 18:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20802', '2080', '2', '2', '2022-10-09 18:45:22', '2022-10-09 18:45:22', '2022-10-11 18:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-09 18:45:22', '132456', '2022-10-09 18:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20804', '2080', '4', '1', '2022-10-11 18:45:22', '2022-10-11 18:45:22', '2022-10-12 18:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-09 18:45:22', '132456', '2022-10-09 18:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20801', '2', '0', '132456', '2022-10-09 18:45:22', '132456', '2022-10-09 18:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20801', '5', 'No', '132456', '2022-10-09 18:45:22', '132456', '2022-10-09 18:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20802', '3', '0', '132456', '2022-10-09 18:45:22', '132456', '2022-10-09 18:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20802', '5', 'No', '132456', '2022-10-09 18:45:22', '132456', '2022-10-09 18:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20804', '5', 'No', '132456', '2022-10-09 18:45:22', '132456', '2022-10-09 18:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20804', '1', '30000413', '132456', '2022-10-09 18:45:22', '132456', '2022-10-09 18:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20801', '20802', '1', '1', '0', '132456', '2022-10-09 18:45:22', '132456', '2022-10-09 18:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20802', '20804', '0', '1', '0', '132456', '2022-10-09 18:45:22', '132456', '2022-10-09 18:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2080', '13', '2080', NULL, 132456, '132456', '2022-10-09 18:45:22', '132456', '2022-10-09 18:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2080', '1', '132456', '132456', '2022-10-09 18:45:22', '132456', '2022-10-09 18:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2080', '2', 'dok', '132456', '2022-10-09 18:45:22', '132456', '2022-10-09 18:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2081', '1', '17', '132456', '2022-10-09 00:45:22', '132456', '2022-10-09 00:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2081', '3', '1', '132456', '2022-10-09 00:45:22', '132456', '2022-10-09 00:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2081', '4', '0', '132456', '2022-10-09 00:45:22', '132456', '2022-10-09 00:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2081', '6', 'Design Challenge #2081', '132456', '2022-10-09 00:45:22', '132456', '2022-10-09 00:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2081', '7', '1.0', '132456', '2022-10-09 00:45:22', '132456', '2022-10-09 00:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2081', '9', 'On', '132456', '2022-10-09 00:45:22', '132456', '2022-10-09 00:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2081', '10', 'On', '132456', '2022-10-09 00:45:22', '132456', '2022-10-09 00:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2081', '11', 'On', '132456', '2022-10-09 00:45:22', '132456', '2022-10-09 00:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2081', '12', 'Yes', '132456', '2022-10-09 00:45:22', '132456', '2022-10-09 00:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2081', '13', 'Yes', '132456', '2022-10-09 00:45:22', '132456', '2022-10-09 00:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2081', '14', 'Open', '132456', '2022-10-09 00:45:22', '132456', '2022-10-09 00:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2081', '16', '0.0', '132456', '2022-10-09 00:45:22', '132456', '2022-10-09 00:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2081', '26', 'On', '132456', '2022-10-09 00:45:22', '132456', '2022-10-09 00:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2081', '29', 'On', '132456', '2022-10-09 00:45:22', '132456', '2022-10-09 00:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2081', '42', 'true', '132456', '2022-10-09 00:45:22', '132456', '2022-10-09 00:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2081', '43', 'true', '132456', '2022-10-09 00:45:22', '132456', '2022-10-09 00:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20811', '2081', '1', '3', '2022-10-09 00:45:22', '2022-10-09 00:45:22', '2022-10-11 00:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-09 00:45:22', '132456', '2022-10-09 00:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20812', '2081', '2', '3', '2022-10-09 00:45:22', '2022-10-09 00:45:22', '2022-10-11 00:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-09 00:45:22', '132456', '2022-10-09 00:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20813', '2081', '3', '2', '2022-10-11 00:45:22', '2022-10-11 00:45:22', '2022-10-11 12:45:22', NULL, NULL, '43200000.0', '132456', '2022-10-09 00:45:22', '132456', '2022-10-09 00:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20814', '2081', '4', '1', '2022-10-11 12:45:22', '2022-10-11 12:45:22', '2022-10-12 12:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-09 00:45:22', '132456', '2022-10-09 00:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('208111', '2081', '11', '1', '2022-10-12 12:45:22', '2022-10-12 12:45:22', '2022-10-13 00:45:22', NULL, NULL, '43200000.0', '132456', '2022-10-09 00:45:22', '132456', '2022-10-09 00:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20811', '2', '0', '132456', '2022-10-09 00:45:22', '132456', '2022-10-09 00:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20811', '5', 'No', '132456', '2022-10-09 00:45:22', '132456', '2022-10-09 00:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20812', '3', '0', '132456', '2022-10-09 00:45:22', '132456', '2022-10-09 00:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20812', '5', 'No', '132456', '2022-10-09 00:45:22', '132456', '2022-10-09 00:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20813', '1', '30000410', '132456', '2022-10-09 00:45:22', '132456', '2022-10-09 00:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20814', '5', 'No', '132456', '2022-10-09 00:45:22', '132456', '2022-10-09 00:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20814', '1', '30000411', '132456', '2022-10-09 00:45:22', '132456', '2022-10-09 00:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('208111', '1', '30000720', '132456', '2022-10-09 00:45:22', '132456', '2022-10-09 00:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20811', '20812', '1', '1', '0', '132456', '2022-10-09 00:45:22', '132456', '2022-10-09 00:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20812', '20813', '0', '1', '0', '132456', '2022-10-09 00:45:22', '132456', '2022-10-09 00:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20813', '20814', '0', '1', '0', '132456', '2022-10-09 00:45:22', '132456', '2022-10-09 00:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20814', '208111', '0', '1', '0', '132456', '2022-10-09 00:45:22', '132456', '2022-10-09 00:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2081', '13', '2081', NULL, 132456, '132456', '2022-10-09 00:45:22', '132456', '2022-10-09 00:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2081', '1', '132456', '132456', '2022-10-09 00:45:22', '132456', '2022-10-09 00:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2081', '2', 'dok', '132456', '2022-10-09 00:45:22', '132456', '2022-10-09 00:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2082', '1', '17', '132456', '2022-10-11 00:45:22', '132456', '2022-10-11 00:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2082', '3', '1', '132456', '2022-10-11 00:45:22', '132456', '2022-10-11 00:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2082', '4', '0', '132456', '2022-10-11 00:45:22', '132456', '2022-10-11 00:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2082', '6', 'Design 2 Round Challenge #2082', '132456', '2022-10-11 00:45:22', '132456', '2022-10-11 00:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2082', '7', '1.0', '132456', '2022-10-11 00:45:22', '132456', '2022-10-11 00:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2082', '9', 'On', '132456', '2022-10-11 00:45:22', '132456', '2022-10-11 00:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2082', '10', 'On', '132456', '2022-10-11 00:45:22', '132456', '2022-10-11 00:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2082', '11', 'On', '132456', '2022-10-11 00:45:22', '132456', '2022-10-11 00:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2082', '12', 'Yes', '132456', '2022-10-11 00:45:22', '132456', '2022-10-11 00:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2082', '13', 'Yes', '132456', '2022-10-11 00:45:22', '132456', '2022-10-11 00:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2082', '14', 'Open', '132456', '2022-10-11 00:45:22', '132456', '2022-10-11 00:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2082', '16', '0.0', '132456', '2022-10-11 00:45:22', '132456', '2022-10-11 00:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2082', '26', 'On', '132456', '2022-10-11 00:45:22', '132456', '2022-10-11 00:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2082', '29', 'On', '132456', '2022-10-11 00:45:22', '132456', '2022-10-11 00:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2082', '42', 'true', '132456', '2022-10-11 00:45:22', '132456', '2022-10-11 00:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2082', '43', 'true', '132456', '2022-10-11 00:45:22', '132456', '2022-10-11 00:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('208213', '2082', '13', '3', '2022-10-11 00:45:22', '2022-10-11 00:45:22', '2022-10-11 00:45:22', NULL, NULL, '0.0', '132456', '2022-10-11 00:45:22', '132456', '2022-10-11 00:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('208214', '2082', '14', '3', '2022-10-11 00:45:22', '2022-10-11 00:45:22', '2022-10-11 01:45:22', NULL, NULL, '3600000.0', '132456', '2022-10-11 00:45:22', '132456', '2022-10-11 00:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20821', '2082', '1', '2', '2022-10-11 01:45:22', '2022-10-11 01:45:22', '2022-10-13 01:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-11 00:45:22', '132456', '2022-10-11 00:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('208215', '2082', '15', '2', '2022-10-11 01:45:22', '2022-10-11 01:45:22', '2022-10-13 01:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-11 00:45:22', '132456', '2022-10-11 00:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('208216', '2082', '16', '1', '2022-10-13 01:45:22', '2022-10-13 01:45:22', '2022-10-13 07:45:22', NULL, NULL, '21600000.0', '132456', '2022-10-11 00:45:22', '132456', '2022-10-11 00:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('208217', '2082', '17', '1', '2022-10-13 07:45:22', '2022-10-13 07:45:22', '2022-10-14 07:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-11 00:45:22', '132456', '2022-10-11 00:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20822', '2082', '2', '2', '2022-10-11 01:45:22', '2022-10-11 01:45:22', '2022-10-15 01:45:22', NULL, NULL, '345600000.0', '132456', '2022-10-11 00:45:22', '132456', '2022-10-11 00:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20823', '2082', '3', '1', '2022-10-15 01:45:22', '2022-10-15 01:45:22', '2022-10-15 13:45:22', NULL, NULL, '43200000.0', '132456', '2022-10-11 00:45:22', '132456', '2022-10-11 00:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20824', '2082', '4', '1', '2022-10-15 13:45:22', '2022-10-15 13:45:22', '2022-10-16 01:45:22', NULL, NULL, '43200000.0', '132456', '2022-10-11 00:45:22', '132456', '2022-10-11 00:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('208211', '2082', '11', '1', '2022-10-16 01:45:22', '2022-10-16 01:45:22', '2022-10-16 13:45:22', NULL, NULL, '43200000.0', '132456', '2022-10-11 00:45:22', '132456', '2022-10-11 00:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20829', '2082', '9', '1', '2022-10-16 13:45:22', '2022-10-16 13:45:22', '2022-10-17 13:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-11 00:45:22', '132456', '2022-10-11 00:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('208210', '2082', '10', '1', '2022-10-17 13:45:22', '2022-10-17 13:45:22', '2022-10-17 19:45:22', NULL, NULL, '21600000.0', '132456', '2022-10-11 00:45:22', '132456', '2022-10-11 00:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('208213', '3', '0', '132456', '2022-10-11 00:45:22', '132456', '2022-10-11 00:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('208213', '5', 'No', '132456', '2022-10-11 00:45:22', '132456', '2022-10-11 00:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('208214', '1', '30000722', '132456', '2022-10-11 00:45:22', '132456', '2022-10-11 00:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('208214', '6', '3', '132456', '2022-10-11 00:45:22', '132456', '2022-10-11 00:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20821', '2', '0', '132456', '2022-10-11 00:45:22', '132456', '2022-10-11 00:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20821', '5', 'No', '132456', '2022-10-11 00:45:22', '132456', '2022-10-11 00:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('208215', '3', '0', '132456', '2022-10-11 00:45:22', '132456', '2022-10-11 00:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('208215', '5', 'No', '132456', '2022-10-11 00:45:22', '132456', '2022-10-11 00:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('208216', '1', '30000416', '132456', '2022-10-11 00:45:22', '132456', '2022-10-11 00:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('208217', '1', '30000417', '132456', '2022-10-11 00:45:22', '132456', '2022-10-11 00:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('208217', '6', '3', '132456', '2022-10-11 00:45:22', '132456', '2022-10-11 00:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20822', '3', '0', '132456', '2022-10-11 00:45:22', '132456', '2022-10-11 00:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20822', '5', 'No', '132456', '2022-10-11 00:45:22', '132456', '2022-10-11 00:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20823', '1', '30000410', '132456', '2022-10-11 00:45:22', '132456', '2022-10-11 00:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20824', '5', 'No', '132456', '2022-10-11 00:45:22', '132456', '2022-10-11 00:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20824', '1', '30000411', '132456', '2022-10-11 00:45:22', '132456', '2022-10-11 00:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20824', '6', '3', '132456', '2022-10-11 00:45:22', '132456', '2022-10-11 00:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('208211', '1', '30000720', '132456', '2022-10-11 00:45:22', '132456', '2022-10-11 00:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20829', '5', 'No', '132456', '2022-10-11 00:45:22', '132456', '2022-10-11 00:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('208210', '5', 'No', '132456', '2022-10-11 00:45:22', '132456', '2022-10-11 00:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('208213', '208214', '1', '1', '0', '132456', '2022-10-11 00:45:22', '132456', '2022-10-11 00:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('208214', '20821', '0', '1', '0', '132456', '2022-10-11 00:45:22', '132456', '2022-10-11 00:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20821', '208215', '0', '1', '0', '132456', '2022-10-11 00:45:22', '132456', '2022-10-11 00:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('208215', '208216', '0', '1', '0', '132456', '2022-10-11 00:45:22', '132456', '2022-10-11 00:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('208216', '208217', '0', '1', '0', '132456', '2022-10-11 00:45:22', '132456', '2022-10-11 00:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('208217', '20822', '0', '1', '0', '132456', '2022-10-11 00:45:22', '132456', '2022-10-11 00:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20822', '20823', '0', '1', '0', '132456', '2022-10-11 00:45:22', '132456', '2022-10-11 00:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20823', '20824', '0', '1', '0', '132456', '2022-10-11 00:45:22', '132456', '2022-10-11 00:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20824', '208211', '0', '1', '0', '132456', '2022-10-11 00:45:22', '132456', '2022-10-11 00:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('208211', '20829', '0', '1', '0', '132456', '2022-10-11 00:45:22', '132456', '2022-10-11 00:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20829', '208210', '0', '1', '0', '132456', '2022-10-11 00:45:22', '132456', '2022-10-11 00:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2082', '13', '2082', NULL, 132456, '132456', '2022-10-11 00:45:22', '132456', '2022-10-11 00:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2082', '1', '132456', '132456', '2022-10-11 00:45:22', '132456', '2022-10-11 00:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2082', '2', 'dok', '132456', '2022-10-11 00:45:22', '132456', '2022-10-11 00:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2083', '1', '39', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2083', '3', '1', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2083', '4', '0', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2083', '6', 'Development Challenge #2083', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2083', '7', '1.0', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2083', '9', 'On', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2083', '10', 'On', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2083', '11', 'On', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2083', '12', 'Yes', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2083', '13', 'Yes', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2083', '14', 'Open', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2083', '16', '0.0', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2083', '26', 'On', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2083', '29', 'On', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2083', '42', 'true', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2083', '43', 'true', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20831', '2083', '1', '2', '2022-10-09 13:45:22', '2022-10-09 13:45:22', '2022-10-11 13:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20832', '2083', '2', '2', '2022-10-09 13:45:22', '2022-10-09 13:45:22', '2022-10-11 13:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20834', '2083', '4', '1', '2022-10-11 13:45:22', '2022-10-11 13:45:22', '2022-10-12 13:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20835', '2083', '5', '1', '2022-10-12 13:45:22', '2022-10-12 13:45:22', '2022-10-13 13:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20836', '2083', '6', '1', '2022-10-13 13:45:22', '2022-10-13 13:45:22', '2022-10-14 01:45:22', NULL, NULL, '43200000.0', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20831', '2', '0', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20831', '5', 'No', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20832', '3', '0', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20832', '5', 'No', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20834', '5', 'No', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20834', '1', '30000413', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20835', '5', 'No', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20835', '4', 'No', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20836', '5', 'No', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20831', '20832', '1', '1', '0', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20832', '20834', '0', '1', '0', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20834', '20835', '0', '1', '0', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20835', '20836', '0', '1', '0', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2083', '13', '2083', NULL, 132456, '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2083', '1', '132456', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2083', '2', 'dok', '132456', '2022-10-09 13:45:22', '132456', '2022-10-09 13:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2084', '1', '40', '132456', '2022-10-06 05:45:22', '132456', '2022-10-06 05:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2084', '3', '1', '132456', '2022-10-06 05:45:22', '132456', '2022-10-06 05:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2084', '4', '0', '132456', '2022-10-06 05:45:22', '132456', '2022-10-06 05:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2084', '6', 'First2Finish Design Challenge #2084', '132456', '2022-10-06 05:45:22', '132456', '2022-10-06 05:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2084', '7', '1.0', '132456', '2022-10-06 05:45:22', '132456', '2022-10-06 05:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2084', '9', 'On', '132456', '2022-10-06 05:45:22', '132456', '2022-10-06 05:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2084', '10', 'On', '132456', '2022-10-06 05:45:22', '132456', '2022-10-06 05:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2084', '11', 'On', '132456', '2022-10-06 05:45:22', '132456', '2022-10-06 05:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2084', '12', 'Yes', '132456', '2022-10-06 05:45:22', '132456', '2022-10-06 05:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2084', '13', 'Yes', '132456', '2022-10-06 05:45:22', '132456', '2022-10-06 05:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2084', '14', 'Open', '132456', '2022-10-06 05:45:22', '132456', '2022-10-06 05:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2084', '16', '0.0', '132456', '2022-10-06 05:45:22', '132456', '2022-10-06 05:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2084', '26', 'On', '132456', '2022-10-06 05:45:22', '132456', '2022-10-06 05:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2084', '29', 'On', '132456', '2022-10-06 05:45:22', '132456', '2022-10-06 05:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2084', '42', 'true', '132456', '2022-10-06 05:45:22', '132456', '2022-10-06 05:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2084', '43', 'true', '132456', '2022-10-06 05:45:22', '132456', '2022-10-06 05:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20841', '2084', '1', '2', '2022-10-06 05:45:22', '2022-10-06 05:45:22', '2022-11-05 05:45:22', NULL, NULL, '2592000000.0', '132456', '2022-10-06 05:45:22', '132456', '2022-10-06 05:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20842', '2084', '2', '2', '2022-10-06 05:45:22', '2022-10-06 05:45:22', '2022-11-05 05:45:22', NULL, NULL, '2592000000.0', '132456', '2022-10-06 05:45:22', '132456', '2022-10-06 05:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('208418', '2084', '18', '3', '2022-10-07 05:45:22', '2022-10-07 05:45:22', '2022-10-08 05:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-06 05:45:22', '132456', '2022-10-06 05:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20841', '2', '0', '132456', '2022-10-06 05:45:22', '132456', '2022-10-06 05:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20841', '5', 'No', '132456', '2022-10-06 05:45:22', '132456', '2022-10-06 05:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20842', '3', '0', '132456', '2022-10-06 05:45:22', '132456', '2022-10-06 05:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20842', '5', 'No', '132456', '2022-10-06 05:45:22', '132456', '2022-10-06 05:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('208418', '1', '30000419', '132456', '2022-10-06 05:45:22', '132456', '2022-10-06 05:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20841', '20842', '1', '1', '0', '132456', '2022-10-06 05:45:22', '132456', '2022-10-06 05:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20842', '208418', '0', '1', '0', '132456', '2022-10-06 05:45:22', '132456', '2022-10-06 05:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2084', '13', '2084', NULL, 132456, '132456', '2022-10-06 05:45:22', '132456', '2022-10-06 05:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2084', '1', '132456', '132456', '2022-10-06 05:45:22', '132456', '2022-10-06 05:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2084', '2', 'dok', '132456', '2022-10-06 05:45:22', '132456', '2022-10-06 05:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2085', '1', '38', '132456', '2022-10-04 14:45:22', '132456', '2022-10-04 14:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2085', '3', '1', '132456', '2022-10-04 14:45:22', '132456', '2022-10-04 14:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2085', '4', '0', '132456', '2022-10-04 14:45:22', '132456', '2022-10-04 14:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2085', '6', 'First2Finish Challenge #2085', '132456', '2022-10-04 14:45:22', '132456', '2022-10-04 14:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2085', '7', '1.0', '132456', '2022-10-04 14:45:22', '132456', '2022-10-04 14:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2085', '9', 'On', '132456', '2022-10-04 14:45:22', '132456', '2022-10-04 14:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2085', '10', 'On', '132456', '2022-10-04 14:45:22', '132456', '2022-10-04 14:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2085', '11', 'On', '132456', '2022-10-04 14:45:22', '132456', '2022-10-04 14:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2085', '12', 'Yes', '132456', '2022-10-04 14:45:22', '132456', '2022-10-04 14:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2085', '13', 'Yes', '132456', '2022-10-04 14:45:22', '132456', '2022-10-04 14:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2085', '14', 'Open', '132456', '2022-10-04 14:45:22', '132456', '2022-10-04 14:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2085', '16', '0.0', '132456', '2022-10-04 14:45:22', '132456', '2022-10-04 14:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2085', '26', 'On', '132456', '2022-10-04 14:45:22', '132456', '2022-10-04 14:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2085', '29', 'On', '132456', '2022-10-04 14:45:22', '132456', '2022-10-04 14:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2085', '42', 'true', '132456', '2022-10-04 14:45:22', '132456', '2022-10-04 14:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2085', '43', 'true', '132456', '2022-10-04 14:45:22', '132456', '2022-10-04 14:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20851', '2085', '1', '2', '2022-10-04 14:45:22', '2022-10-04 14:45:22', '2022-11-03 14:45:22', NULL, NULL, '2592000000.0', '132456', '2022-10-04 14:45:22', '132456', '2022-10-04 14:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20852', '2085', '2', '2', '2022-10-04 14:45:22', '2022-10-04 14:45:22', '2022-11-03 14:45:22', NULL, NULL, '2592000000.0', '132456', '2022-10-04 14:45:22', '132456', '2022-10-04 14:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('208518', '2085', '18', '3', '2022-10-05 14:45:22', '2022-10-05 14:45:22', '2022-10-06 14:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-04 14:45:22', '132456', '2022-10-04 14:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20851', '2', '0', '132456', '2022-10-04 14:45:22', '132456', '2022-10-04 14:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20851', '5', 'No', '132456', '2022-10-04 14:45:22', '132456', '2022-10-04 14:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20852', '3', '0', '132456', '2022-10-04 14:45:22', '132456', '2022-10-04 14:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20852', '5', 'No', '132456', '2022-10-04 14:45:22', '132456', '2022-10-04 14:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('208518', '1', '30000419', '132456', '2022-10-04 14:45:22', '132456', '2022-10-04 14:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20851', '20852', '1', '1', '0', '132456', '2022-10-04 14:45:22', '132456', '2022-10-04 14:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20852', '208518', '0', '1', '0', '132456', '2022-10-04 14:45:22', '132456', '2022-10-04 14:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2085', '13', '2085', NULL, 132456, '132456', '2022-10-04 14:45:22', '132456', '2022-10-04 14:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2085', '1', '132456', '132456', '2022-10-04 14:45:22', '132456', '2022-10-04 14:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2085', '2', 'dok', '132456', '2022-10-04 14:45:22', '132456', '2022-10-04 14:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2086', '1', '37', '132456', '2022-10-10 12:45:22', '132456', '2022-10-10 12:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2086', '3', '1', '132456', '2022-10-10 12:45:22', '132456', '2022-10-10 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2086', '4', '0', '132456', '2022-10-10 12:45:22', '132456', '2022-10-10 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2086', '6', 'Data Science Challenge #2086', '132456', '2022-10-10 12:45:22', '132456', '2022-10-10 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2086', '7', '1.0', '132456', '2022-10-10 12:45:22', '132456', '2022-10-10 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2086', '9', 'On', '132456', '2022-10-10 12:45:22', '132456', '2022-10-10 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2086', '10', 'On', '132456', '2022-10-10 12:45:22', '132456', '2022-10-10 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2086', '11', 'On', '132456', '2022-10-10 12:45:22', '132456', '2022-10-10 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2086', '12', 'Yes', '132456', '2022-10-10 12:45:22', '132456', '2022-10-10 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2086', '13', 'Yes', '132456', '2022-10-10 12:45:22', '132456', '2022-10-10 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2086', '14', 'Open', '132456', '2022-10-10 12:45:22', '132456', '2022-10-10 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2086', '16', '0.0', '132456', '2022-10-10 12:45:22', '132456', '2022-10-10 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2086', '26', 'On', '132456', '2022-10-10 12:45:22', '132456', '2022-10-10 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2086', '29', 'On', '132456', '2022-10-10 12:45:22', '132456', '2022-10-10 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2086', '42', 'true', '132456', '2022-10-10 12:45:22', '132456', '2022-10-10 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2086', '43', 'true', '132456', '2022-10-10 12:45:22', '132456', '2022-10-10 12:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20861', '2086', '1', '2', '2022-10-10 12:45:22', '2022-10-10 12:45:22', '2022-10-12 12:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-10 12:45:22', '132456', '2022-10-10 12:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20862', '2086', '2', '2', '2022-10-10 12:45:22', '2022-10-10 12:45:22', '2022-10-12 12:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-10 12:45:22', '132456', '2022-10-10 12:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20864', '2086', '4', '1', '2022-10-12 12:45:22', '2022-10-12 12:45:22', '2022-10-13 12:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-10 12:45:22', '132456', '2022-10-10 12:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20861', '2', '0', '132456', '2022-10-10 12:45:22', '132456', '2022-10-10 12:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20861', '5', 'No', '132456', '2022-10-10 12:45:22', '132456', '2022-10-10 12:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20862', '3', '0', '132456', '2022-10-10 12:45:22', '132456', '2022-10-10 12:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20862', '5', 'No', '132456', '2022-10-10 12:45:22', '132456', '2022-10-10 12:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20864', '5', 'No', '132456', '2022-10-10 12:45:22', '132456', '2022-10-10 12:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20864', '1', '30000413', '132456', '2022-10-10 12:45:22', '132456', '2022-10-10 12:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20861', '20862', '1', '1', '0', '132456', '2022-10-10 12:45:22', '132456', '2022-10-10 12:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20862', '20864', '0', '1', '0', '132456', '2022-10-10 12:45:22', '132456', '2022-10-10 12:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2086', '13', '2086', NULL, 132456, '132456', '2022-10-10 12:45:22', '132456', '2022-10-10 12:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2086', '1', '132456', '132456', '2022-10-10 12:45:22', '132456', '2022-10-10 12:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2086', '2', 'dok', '132456', '2022-10-10 12:45:22', '132456', '2022-10-10 12:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2087', '1', '38', '132456', '2022-10-05 16:45:22', '132456', '2022-10-05 16:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2087', '3', '1', '132456', '2022-10-05 16:45:22', '132456', '2022-10-05 16:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2087', '4', '0', '132456', '2022-10-05 16:45:22', '132456', '2022-10-05 16:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2087', '6', 'First2Finish Challenge #2087', '132456', '2022-10-05 16:45:22', '132456', '2022-10-05 16:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2087', '7', '1.0', '132456', '2022-10-05 16:45:22', '132456', '2022-10-05 16:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2087', '9', 'On', '132456', '2022-10-05 16:45:22', '132456', '2022-10-05 16:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2087', '10', 'On', '132456', '2022-10-05 16:45:22', '132456', '2022-10-05 16:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2087', '11', 'On', '132456', '2022-10-05 16:45:22', '132456', '2022-10-05 16:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2087', '12', 'Yes', '132456', '2022-10-05 16:45:22', '132456', '2022-10-05 16:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2087', '13', 'Yes', '132456', '2022-10-05 16:45:22', '132456', '2022-10-05 16:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2087', '14', 'Open', '132456', '2022-10-05 16:45:22', '132456', '2022-10-05 16:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2087', '16', '0.0', '132456', '2022-10-05 16:45:22', '132456', '2022-10-05 16:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2087', '26', 'On', '132456', '2022-10-05 16:45:22', '132456', '2022-10-05 16:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2087', '29', 'On', '132456', '2022-10-05 16:45:22', '132456', '2022-10-05 16:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2087', '42', 'true', '132456', '2022-10-05 16:45:22', '132456', '2022-10-05 16:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2087', '43', 'true', '132456', '2022-10-05 16:45:22', '132456', '2022-10-05 16:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20871', '2087', '1', '2', '2022-10-05 16:45:22', '2022-10-05 16:45:22', '2022-11-04 16:45:22', NULL, NULL, '2592000000.0', '132456', '2022-10-05 16:45:22', '132456', '2022-10-05 16:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20872', '2087', '2', '2', '2022-10-05 16:45:22', '2022-10-05 16:45:22', '2022-11-04 16:45:22', NULL, NULL, '2592000000.0', '132456', '2022-10-05 16:45:22', '132456', '2022-10-05 16:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('208718', '2087', '18', '3', '2022-10-06 16:45:22', '2022-10-06 16:45:22', '2022-10-07 16:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-05 16:45:22', '132456', '2022-10-05 16:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20871', '2', '0', '132456', '2022-10-05 16:45:22', '132456', '2022-10-05 16:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20871', '5', 'No', '132456', '2022-10-05 16:45:22', '132456', '2022-10-05 16:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20872', '3', '0', '132456', '2022-10-05 16:45:22', '132456', '2022-10-05 16:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20872', '5', 'No', '132456', '2022-10-05 16:45:22', '132456', '2022-10-05 16:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('208718', '1', '30000419', '132456', '2022-10-05 16:45:22', '132456', '2022-10-05 16:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20871', '20872', '1', '1', '0', '132456', '2022-10-05 16:45:22', '132456', '2022-10-05 16:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20872', '208718', '0', '1', '0', '132456', '2022-10-05 16:45:22', '132456', '2022-10-05 16:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2087', '13', '2087', NULL, 132456, '132456', '2022-10-05 16:45:22', '132456', '2022-10-05 16:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2087', '1', '132456', '132456', '2022-10-05 16:45:22', '132456', '2022-10-05 16:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2087', '2', 'dok', '132456', '2022-10-05 16:45:22', '132456', '2022-10-05 16:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2088', '1', '9', '132456', '2022-10-04 01:45:22', '132456', '2022-10-04 01:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2088', '3', '1', '132456', '2022-10-04 01:45:22', '132456', '2022-10-04 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2088', '4', '0', '132456', '2022-10-04 01:45:22', '132456', '2022-10-04 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2088', '6', 'QA Challenge #2088', '132456', '2022-10-04 01:45:22', '132456', '2022-10-04 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2088', '7', '1.0', '132456', '2022-10-04 01:45:22', '132456', '2022-10-04 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2088', '9', 'On', '132456', '2022-10-04 01:45:22', '132456', '2022-10-04 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2088', '10', 'On', '132456', '2022-10-04 01:45:22', '132456', '2022-10-04 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2088', '11', 'On', '132456', '2022-10-04 01:45:22', '132456', '2022-10-04 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2088', '12', 'Yes', '132456', '2022-10-04 01:45:22', '132456', '2022-10-04 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2088', '13', 'Yes', '132456', '2022-10-04 01:45:22', '132456', '2022-10-04 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2088', '14', 'Open', '132456', '2022-10-04 01:45:22', '132456', '2022-10-04 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2088', '16', '0.0', '132456', '2022-10-04 01:45:22', '132456', '2022-10-04 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2088', '26', 'On', '132456', '2022-10-04 01:45:22', '132456', '2022-10-04 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2088', '29', 'On', '132456', '2022-10-04 01:45:22', '132456', '2022-10-04 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2088', '42', 'true', '132456', '2022-10-04 01:45:22', '132456', '2022-10-04 01:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2088', '43', 'true', '132456', '2022-10-04 01:45:22', '132456', '2022-10-04 01:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20881', '2088', '1', '3', '2022-10-04 01:45:22', '2022-10-04 01:45:22', '2022-10-06 01:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-04 01:45:22', '132456', '2022-10-04 01:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20882', '2088', '2', '3', '2022-10-04 01:45:22', '2022-10-04 01:45:22', '2022-10-06 01:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-04 01:45:22', '132456', '2022-10-04 01:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20884', '2088', '4', '3', '2022-10-06 01:45:22', '2022-10-06 01:45:22', '2022-10-07 01:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-04 01:45:22', '132456', '2022-10-04 01:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20881', '2', '0', '132456', '2022-10-04 01:45:22', '132456', '2022-10-04 01:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20881', '5', 'No', '132456', '2022-10-04 01:45:22', '132456', '2022-10-04 01:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20882', '3', '0', '132456', '2022-10-04 01:45:22', '132456', '2022-10-04 01:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20882', '5', 'No', '132456', '2022-10-04 01:45:22', '132456', '2022-10-04 01:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20884', '5', 'No', '132456', '2022-10-04 01:45:22', '132456', '2022-10-04 01:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20884', '1', '30000413', '132456', '2022-10-04 01:45:22', '132456', '2022-10-04 01:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20881', '20882', '1', '1', '0', '132456', '2022-10-04 01:45:22', '132456', '2022-10-04 01:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20882', '20884', '0', '1', '0', '132456', '2022-10-04 01:45:22', '132456', '2022-10-04 01:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2088', '13', '2088', NULL, 132456, '132456', '2022-10-04 01:45:22', '132456', '2022-10-04 01:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2088', '1', '132456', '132456', '2022-10-04 01:45:22', '132456', '2022-10-04 01:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2088', '2', 'dok', '132456', '2022-10-04 01:45:22', '132456', '2022-10-04 01:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2089', '1', '37', '132456', '2022-10-08 17:45:22', '132456', '2022-10-08 17:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2089', '3', '1', '132456', '2022-10-08 17:45:22', '132456', '2022-10-08 17:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2089', '4', '0', '132456', '2022-10-08 17:45:22', '132456', '2022-10-08 17:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2089', '6', 'Data Science Challenge #2089', '132456', '2022-10-08 17:45:22', '132456', '2022-10-08 17:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2089', '7', '1.0', '132456', '2022-10-08 17:45:22', '132456', '2022-10-08 17:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2089', '9', 'On', '132456', '2022-10-08 17:45:22', '132456', '2022-10-08 17:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2089', '10', 'On', '132456', '2022-10-08 17:45:22', '132456', '2022-10-08 17:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2089', '11', 'On', '132456', '2022-10-08 17:45:22', '132456', '2022-10-08 17:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2089', '12', 'Yes', '132456', '2022-10-08 17:45:22', '132456', '2022-10-08 17:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2089', '13', 'Yes', '132456', '2022-10-08 17:45:22', '132456', '2022-10-08 17:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2089', '14', 'Open', '132456', '2022-10-08 17:45:22', '132456', '2022-10-08 17:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2089', '16', '0.0', '132456', '2022-10-08 17:45:22', '132456', '2022-10-08 17:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2089', '26', 'On', '132456', '2022-10-08 17:45:22', '132456', '2022-10-08 17:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2089', '29', 'On', '132456', '2022-10-08 17:45:22', '132456', '2022-10-08 17:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2089', '42', 'true', '132456', '2022-10-08 17:45:22', '132456', '2022-10-08 17:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2089', '43', 'true', '132456', '2022-10-08 17:45:22', '132456', '2022-10-08 17:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20891', '2089', '1', '3', '2022-10-08 17:45:22', '2022-10-08 17:45:22', '2022-10-10 17:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-08 17:45:22', '132456', '2022-10-08 17:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20892', '2089', '2', '3', '2022-10-08 17:45:22', '2022-10-08 17:45:22', '2022-10-10 17:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-08 17:45:22', '132456', '2022-10-08 17:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20894', '2089', '4', '2', '2022-10-10 17:45:22', '2022-10-10 17:45:22', '2022-10-11 17:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-08 17:45:22', '132456', '2022-10-08 17:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20891', '2', '0', '132456', '2022-10-08 17:45:22', '132456', '2022-10-08 17:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20891', '5', 'No', '132456', '2022-10-08 17:45:22', '132456', '2022-10-08 17:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20892', '3', '0', '132456', '2022-10-08 17:45:22', '132456', '2022-10-08 17:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20892', '5', 'No', '132456', '2022-10-08 17:45:22', '132456', '2022-10-08 17:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20894', '5', 'No', '132456', '2022-10-08 17:45:22', '132456', '2022-10-08 17:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20894', '1', '30000413', '132456', '2022-10-08 17:45:22', '132456', '2022-10-08 17:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20891', '20892', '1', '1', '0', '132456', '2022-10-08 17:45:22', '132456', '2022-10-08 17:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20892', '20894', '0', '1', '0', '132456', '2022-10-08 17:45:22', '132456', '2022-10-08 17:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2089', '13', '2089', NULL, 132456, '132456', '2022-10-08 17:45:22', '132456', '2022-10-08 17:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2089', '1', '132456', '132456', '2022-10-08 17:45:22', '132456', '2022-10-08 17:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2089', '2', 'dok', '132456', '2022-10-08 17:45:22', '132456', '2022-10-08 17:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2090', '1', '17', '132456', '2022-10-06 15:45:22', '132456', '2022-10-06 15:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2090', '3', '1', '132456', '2022-10-06 15:45:22', '132456', '2022-10-06 15:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2090', '4', '0', '132456', '2022-10-06 15:45:22', '132456', '2022-10-06 15:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2090', '6', 'Design Challenge #2090', '132456', '2022-10-06 15:45:22', '132456', '2022-10-06 15:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2090', '7', '1.0', '132456', '2022-10-06 15:45:22', '132456', '2022-10-06 15:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2090', '9', 'On', '132456', '2022-10-06 15:45:22', '132456', '2022-10-06 15:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2090', '10', 'On', '132456', '2022-10-06 15:45:22', '132456', '2022-10-06 15:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2090', '11', 'On', '132456', '2022-10-06 15:45:22', '132456', '2022-10-06 15:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2090', '12', 'Yes', '132456', '2022-10-06 15:45:22', '132456', '2022-10-06 15:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2090', '13', 'Yes', '132456', '2022-10-06 15:45:22', '132456', '2022-10-06 15:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2090', '14', 'Open', '132456', '2022-10-06 15:45:22', '132456', '2022-10-06 15:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2090', '16', '0.0', '132456', '2022-10-06 15:45:22', '132456', '2022-10-06 15:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2090', '26', 'On', '132456', '2022-10-06 15:45:22', '132456', '2022-10-06 15:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2090', '29', 'On', '132456', '2022-10-06 15:45:22', '132456', '2022-10-06 15:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2090', '42', 'true', '132456', '2022-10-06 15:45:22', '132456', '2022-10-06 15:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2090', '43', 'true', '132456', '2022-10-06 15:45:22', '132456', '2022-10-06 15:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20901', '2090', '1', '3', '2022-10-06 15:45:22', '2022-10-06 15:45:22', '2022-10-08 15:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-06 15:45:22', '132456', '2022-10-06 15:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20902', '2090', '2', '3', '2022-10-06 15:45:22', '2022-10-06 15:45:22', '2022-10-08 15:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-06 15:45:22', '132456', '2022-10-06 15:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20903', '2090', '3', '3', '2022-10-08 15:45:22', '2022-10-08 15:45:22', '2022-10-09 03:45:22', NULL, NULL, '43200000.0', '132456', '2022-10-06 15:45:22', '132456', '2022-10-06 15:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20904', '2090', '4', '3', '2022-10-09 03:45:22', '2022-10-09 03:45:22', '2022-10-10 03:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-06 15:45:22', '132456', '2022-10-06 15:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('209011', '2090', '11', '3', '2022-10-10 03:45:22', '2022-10-10 03:45:22', '2022-10-10 15:45:22', NULL, NULL, '43200000.0', '132456', '2022-10-06 15:45:22', '132456', '2022-10-06 15:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20901', '2', '0', '132456', '2022-10-06 15:45:22', '132456', '2022-10-06 15:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20901', '5', 'No', '132456', '2022-10-06 15:45:22', '132456', '2022-10-06 15:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20902', '3', '0', '132456', '2022-10-06 15:45:22', '132456', '2022-10-06 15:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20902', '5', 'No', '132456', '2022-10-06 15:45:22', '132456', '2022-10-06 15:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20903', '1', '30000410', '132456', '2022-10-06 15:45:22', '132456', '2022-10-06 15:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20904', '5', 'No', '132456', '2022-10-06 15:45:22', '132456', '2022-10-06 15:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20904', '1', '30000411', '132456', '2022-10-06 15:45:22', '132456', '2022-10-06 15:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('209011', '1', '30000720', '132456', '2022-10-06 15:45:22', '132456', '2022-10-06 15:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20901', '20902', '1', '1', '0', '132456', '2022-10-06 15:45:22', '132456', '2022-10-06 15:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20902', '20903', '0', '1', '0', '132456', '2022-10-06 15:45:22', '132456', '2022-10-06 15:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20903', '20904', '0', '1', '0', '132456', '2022-10-06 15:45:22', '132456', '2022-10-06 15:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20904', '209011', '0', '1', '0', '132456', '2022-10-06 15:45:22', '132456', '2022-10-06 15:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2090', '13', '2090', NULL, 132456, '132456', '2022-10-06 15:45:22', '132456', '2022-10-06 15:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2090', '1', '132456', '132456', '2022-10-06 15:45:22', '132456', '2022-10-06 15:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2090', '2', 'dok', '132456', '2022-10-06 15:45:22', '132456', '2022-10-06 15:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2091', '1', '40', '132456', '2022-10-10 02:45:22', '132456', '2022-10-10 02:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2091', '3', '1', '132456', '2022-10-10 02:45:22', '132456', '2022-10-10 02:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2091', '4', '0', '132456', '2022-10-10 02:45:22', '132456', '2022-10-10 02:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2091', '6', 'First2Finish Design Challenge #2091', '132456', '2022-10-10 02:45:22', '132456', '2022-10-10 02:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2091', '7', '1.0', '132456', '2022-10-10 02:45:22', '132456', '2022-10-10 02:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2091', '9', 'On', '132456', '2022-10-10 02:45:22', '132456', '2022-10-10 02:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2091', '10', 'On', '132456', '2022-10-10 02:45:22', '132456', '2022-10-10 02:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2091', '11', 'On', '132456', '2022-10-10 02:45:22', '132456', '2022-10-10 02:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2091', '12', 'Yes', '132456', '2022-10-10 02:45:22', '132456', '2022-10-10 02:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2091', '13', 'Yes', '132456', '2022-10-10 02:45:22', '132456', '2022-10-10 02:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2091', '14', 'Open', '132456', '2022-10-10 02:45:22', '132456', '2022-10-10 02:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2091', '16', '0.0', '132456', '2022-10-10 02:45:22', '132456', '2022-10-10 02:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2091', '26', 'On', '132456', '2022-10-10 02:45:22', '132456', '2022-10-10 02:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2091', '29', 'On', '132456', '2022-10-10 02:45:22', '132456', '2022-10-10 02:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2091', '42', 'true', '132456', '2022-10-10 02:45:22', '132456', '2022-10-10 02:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2091', '43', 'true', '132456', '2022-10-10 02:45:22', '132456', '2022-10-10 02:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20911', '2091', '1', '2', '2022-10-10 02:45:22', '2022-10-10 02:45:22', '2022-11-09 02:45:22', NULL, NULL, '2592000000.0', '132456', '2022-10-10 02:45:22', '132456', '2022-10-10 02:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20912', '2091', '2', '2', '2022-10-10 02:45:22', '2022-10-10 02:45:22', '2022-11-09 02:45:22', NULL, NULL, '2592000000.0', '132456', '2022-10-10 02:45:22', '132456', '2022-10-10 02:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('209118', '2091', '18', '1', '2022-10-11 02:45:22', '2022-10-11 02:45:22', '2022-10-12 02:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-10 02:45:22', '132456', '2022-10-10 02:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20911', '2', '0', '132456', '2022-10-10 02:45:22', '132456', '2022-10-10 02:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20911', '5', 'No', '132456', '2022-10-10 02:45:22', '132456', '2022-10-10 02:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20912', '3', '0', '132456', '2022-10-10 02:45:22', '132456', '2022-10-10 02:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20912', '5', 'No', '132456', '2022-10-10 02:45:22', '132456', '2022-10-10 02:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('209118', '1', '30000419', '132456', '2022-10-10 02:45:22', '132456', '2022-10-10 02:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20911', '20912', '1', '1', '0', '132456', '2022-10-10 02:45:22', '132456', '2022-10-10 02:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20912', '209118', '0', '1', '0', '132456', '2022-10-10 02:45:22', '132456', '2022-10-10 02:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2091', '13', '2091', NULL, 132456, '132456', '2022-10-10 02:45:22', '132456', '2022-10-10 02:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2091', '1', '132456', '132456', '2022-10-10 02:45:22', '132456', '2022-10-10 02:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2091', '2', 'dok', '132456', '2022-10-10 02:45:22', '132456', '2022-10-10 02:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2092', '1', '9', '132456', '2022-10-07 00:45:22', '132456', '2022-10-07 00:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2092', '3', '1', '132456', '2022-10-07 00:45:22', '132456', '2022-10-07 00:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2092', '4', '0', '132456', '2022-10-07 00:45:22', '132456', '2022-10-07 00:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2092', '6', 'QA Challenge #2092', '132456', '2022-10-07 00:45:22', '132456', '2022-10-07 00:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2092', '7', '1.0', '132456', '2022-10-07 00:45:22', '132456', '2022-10-07 00:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2092', '9', 'On', '132456', '2022-10-07 00:45:22', '132456', '2022-10-07 00:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2092', '10', 'On', '132456', '2022-10-07 00:45:22', '132456', '2022-10-07 00:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2092', '11', 'On', '132456', '2022-10-07 00:45:22', '132456', '2022-10-07 00:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2092', '12', 'Yes', '132456', '2022-10-07 00:45:22', '132456', '2022-10-07 00:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2092', '13', 'Yes', '132456', '2022-10-07 00:45:22', '132456', '2022-10-07 00:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2092', '14', 'Open', '132456', '2022-10-07 00:45:22', '132456', '2022-10-07 00:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2092', '16', '0.0', '132456', '2022-10-07 00:45:22', '132456', '2022-10-07 00:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2092', '26', 'On', '132456', '2022-10-07 00:45:22', '132456', '2022-10-07 00:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2092', '29', 'On', '132456', '2022-10-07 00:45:22', '132456', '2022-10-07 00:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2092', '42', 'true', '132456', '2022-10-07 00:45:22', '132456', '2022-10-07 00:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2092', '43', 'true', '132456', '2022-10-07 00:45:22', '132456', '2022-10-07 00:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20921', '2092', '1', '3', '2022-10-07 00:45:22', '2022-10-07 00:45:22', '2022-10-09 00:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-07 00:45:22', '132456', '2022-10-07 00:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20922', '2092', '2', '3', '2022-10-07 00:45:22', '2022-10-07 00:45:22', '2022-10-09 00:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-07 00:45:22', '132456', '2022-10-07 00:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20924', '2092', '4', '3', '2022-10-09 00:45:22', '2022-10-09 00:45:22', '2022-10-10 00:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-07 00:45:22', '132456', '2022-10-07 00:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20921', '2', '0', '132456', '2022-10-07 00:45:22', '132456', '2022-10-07 00:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20921', '5', 'No', '132456', '2022-10-07 00:45:22', '132456', '2022-10-07 00:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20922', '3', '0', '132456', '2022-10-07 00:45:22', '132456', '2022-10-07 00:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20922', '5', 'No', '132456', '2022-10-07 00:45:22', '132456', '2022-10-07 00:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20924', '5', 'No', '132456', '2022-10-07 00:45:22', '132456', '2022-10-07 00:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20924', '1', '30000413', '132456', '2022-10-07 00:45:22', '132456', '2022-10-07 00:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20921', '20922', '1', '1', '0', '132456', '2022-10-07 00:45:22', '132456', '2022-10-07 00:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20922', '20924', '0', '1', '0', '132456', '2022-10-07 00:45:22', '132456', '2022-10-07 00:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2092', '13', '2092', NULL, 132456, '132456', '2022-10-07 00:45:22', '132456', '2022-10-07 00:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2092', '1', '132456', '132456', '2022-10-07 00:45:22', '132456', '2022-10-07 00:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2092', '2', 'dok', '132456', '2022-10-07 00:45:22', '132456', '2022-10-07 00:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2093', '1', '38', '132456', '2022-10-10 13:45:22', '132456', '2022-10-10 13:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2093', '3', '1', '132456', '2022-10-10 13:45:22', '132456', '2022-10-10 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2093', '4', '0', '132456', '2022-10-10 13:45:22', '132456', '2022-10-10 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2093', '6', 'First2Finish Challenge #2093', '132456', '2022-10-10 13:45:22', '132456', '2022-10-10 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2093', '7', '1.0', '132456', '2022-10-10 13:45:22', '132456', '2022-10-10 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2093', '9', 'On', '132456', '2022-10-10 13:45:22', '132456', '2022-10-10 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2093', '10', 'On', '132456', '2022-10-10 13:45:22', '132456', '2022-10-10 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2093', '11', 'On', '132456', '2022-10-10 13:45:22', '132456', '2022-10-10 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2093', '12', 'Yes', '132456', '2022-10-10 13:45:22', '132456', '2022-10-10 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2093', '13', 'Yes', '132456', '2022-10-10 13:45:22', '132456', '2022-10-10 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2093', '14', 'Open', '132456', '2022-10-10 13:45:22', '132456', '2022-10-10 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2093', '16', '0.0', '132456', '2022-10-10 13:45:22', '132456', '2022-10-10 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2093', '26', 'On', '132456', '2022-10-10 13:45:22', '132456', '2022-10-10 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2093', '29', 'On', '132456', '2022-10-10 13:45:22', '132456', '2022-10-10 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2093', '42', 'true', '132456', '2022-10-10 13:45:22', '132456', '2022-10-10 13:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2093', '43', 'true', '132456', '2022-10-10 13:45:22', '132456', '2022-10-10 13:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20931', '2093', '1', '2', '2022-10-10 13:45:22', '2022-10-10 13:45:22', '2022-11-09 13:45:22', NULL, NULL, '2592000000.0', '132456', '2022-10-10 13:45:22', '132456', '2022-10-10 13:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20932', '2093', '2', '2', '2022-10-10 13:45:22', '2022-10-10 13:45:22', '2022-11-09 13:45:22', NULL, NULL, '2592000000.0', '132456', '2022-10-10 13:45:22', '132456', '2022-10-10 13:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('209318', '2093', '18', '1', '2022-10-11 13:45:22', '2022-10-11 13:45:22', '2022-10-12 13:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-10 13:45:22', '132456', '2022-10-10 13:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20931', '2', '0', '132456', '2022-10-10 13:45:22', '132456', '2022-10-10 13:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20931', '5', 'No', '132456', '2022-10-10 13:45:22', '132456', '2022-10-10 13:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20932', '3', '0', '132456', '2022-10-10 13:45:22', '132456', '2022-10-10 13:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20932', '5', 'No', '132456', '2022-10-10 13:45:22', '132456', '2022-10-10 13:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('209318', '1', '30000419', '132456', '2022-10-10 13:45:22', '132456', '2022-10-10 13:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20931', '20932', '1', '1', '0', '132456', '2022-10-10 13:45:22', '132456', '2022-10-10 13:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20932', '209318', '0', '1', '0', '132456', '2022-10-10 13:45:22', '132456', '2022-10-10 13:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2093', '13', '2093', NULL, 132456, '132456', '2022-10-10 13:45:22', '132456', '2022-10-10 13:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2093', '1', '132456', '132456', '2022-10-10 13:45:22', '132456', '2022-10-10 13:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2093', '2', 'dok', '132456', '2022-10-10 13:45:22', '132456', '2022-10-10 13:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2094', '1', '17', '132456', '2022-10-08 18:45:22', '132456', '2022-10-08 18:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2094', '3', '1', '132456', '2022-10-08 18:45:22', '132456', '2022-10-08 18:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2094', '4', '0', '132456', '2022-10-08 18:45:22', '132456', '2022-10-08 18:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2094', '6', 'Design 2 Round Challenge #2094', '132456', '2022-10-08 18:45:22', '132456', '2022-10-08 18:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2094', '7', '1.0', '132456', '2022-10-08 18:45:22', '132456', '2022-10-08 18:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2094', '9', 'On', '132456', '2022-10-08 18:45:22', '132456', '2022-10-08 18:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2094', '10', 'On', '132456', '2022-10-08 18:45:22', '132456', '2022-10-08 18:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2094', '11', 'On', '132456', '2022-10-08 18:45:22', '132456', '2022-10-08 18:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2094', '12', 'Yes', '132456', '2022-10-08 18:45:22', '132456', '2022-10-08 18:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2094', '13', 'Yes', '132456', '2022-10-08 18:45:22', '132456', '2022-10-08 18:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2094', '14', 'Open', '132456', '2022-10-08 18:45:22', '132456', '2022-10-08 18:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2094', '16', '0.0', '132456', '2022-10-08 18:45:22', '132456', '2022-10-08 18:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2094', '26', 'On', '132456', '2022-10-08 18:45:22', '132456', '2022-10-08 18:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2094', '29', 'On', '132456', '2022-10-08 18:45:22', '132456', '2022-10-08 18:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2094', '42', 'true', '132456', '2022-10-08 18:45:22', '132456', '2022-10-08 18:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2094', '43', 'true', '132456', '2022-10-08 18:45:22', '132456', '2022-10-08 18:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('209413', '2094', '13', '3', '2022-10-08 18:45:22', '2022-10-08 18:45:22', '2022-10-08 18:45:22', NULL, NULL, '0.0', '132456', '2022-10-08 18:45:22', '132456', '2022-10-08 18:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('209414', '2094', '14', '3', '2022-10-08 18:45:22', '2022-10-08 18:45:22', '2022-10-08 19:45:22', NULL, NULL, '3600000.0', '132456', '2022-10-08 18:45:22', '132456', '2022-10-08 18:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20941', '2094', '1', '3', '2022-10-08 19:45:22', '2022-10-08 19:45:22', '2022-10-10 19:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-08 18:45:22', '132456', '2022-10-08 18:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('209415', '2094', '15', '3', '2022-10-08 19:45:22', '2022-10-08 19:45:22', '2022-10-10 19:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-08 18:45:22', '132456', '2022-10-08 18:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('209416', '2094', '16', '3', '2022-10-10 19:45:22', '2022-10-10 19:45:22', '2022-10-11 01:45:22', NULL, NULL, '21600000.0', '132456', '2022-10-08 18:45:22', '132456', '2022-10-08 18:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('209417', '2094', '17', '2', '2022-10-11 01:45:22', '2022-10-11 01:45:22', '2022-10-12 01:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-08 18:45:22', '132456', '2022-10-08 18:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20942', '2094', '2', '2', '2022-10-08 19:45:22', '2022-10-08 19:45:22', '2022-10-12 19:45:22', NULL, NULL, '345600000.0', '132456', '2022-10-08 18:45:22', '132456', '2022-10-08 18:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20943', '2094', '3', '1', '2022-10-12 19:45:22', '2022-10-12 19:45:22', '2022-10-13 07:45:22', NULL, NULL, '43200000.0', '132456', '2022-10-08 18:45:22', '132456', '2022-10-08 18:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20944', '2094', '4', '1', '2022-10-13 07:45:22', '2022-10-13 07:45:22', '2022-10-13 19:45:22', NULL, NULL, '43200000.0', '132456', '2022-10-08 18:45:22', '132456', '2022-10-08 18:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('209411', '2094', '11', '1', '2022-10-13 19:45:22', '2022-10-13 19:45:22', '2022-10-14 07:45:22', NULL, NULL, '43200000.0', '132456', '2022-10-08 18:45:22', '132456', '2022-10-08 18:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20949', '2094', '9', '1', '2022-10-14 07:45:22', '2022-10-14 07:45:22', '2022-10-15 07:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-08 18:45:22', '132456', '2022-10-08 18:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('209410', '2094', '10', '1', '2022-10-15 07:45:22', '2022-10-15 07:45:22', '2022-10-15 13:45:22', NULL, NULL, '21600000.0', '132456', '2022-10-08 18:45:22', '132456', '2022-10-08 18:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('209413', '3', '0', '132456', '2022-10-08 18:45:22', '132456', '2022-10-08 18:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('209413', '5', 'No', '132456', '2022-10-08 18:45:22', '132456', '2022-10-08 18:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('209414', '1', '30000722', '132456', '2022-10-08 18:45:22', '132456', '2022-10-08 18:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('209414', '6', '3', '132456', '2022-10-08 18:45:22', '132456', '2022-10-08 18:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20941', '2', '0', '132456', '2022-10-08 18:45:22', '132456', '2022-10-08 18:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20941', '5', 'No', '132456', '2022-10-08 18:45:22', '132456', '2022-10-08 18:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('209415', '3', '0', '132456', '2022-10-08 18:45:22', '132456', '2022-10-08 18:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('209415', '5', 'No', '132456', '2022-10-08 18:45:22', '132456', '2022-10-08 18:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('209416', '1', '30000416', '132456', '2022-10-08 18:45:22', '132456', '2022-10-08 18:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('209417', '1', '30000417', '132456', '2022-10-08 18:45:22', '132456', '2022-10-08 18:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('209417', '6', '3', '132456', '2022-10-08 18:45:22', '132456', '2022-10-08 18:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20942', '3', '0', '132456', '2022-10-08 18:45:22', '132456', '2022-10-08 18:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20942', '5', 'No', '132456', '2022-10-08 18:45:22', '132456', '2022-10-08 18:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20943', '1', '30000410', '132456', '2022-10-08 18:45:22', '132456', '2022-10-08 18:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20944', '5', 'No', '132456', '2022-10-08 18:45:22', '132456', '2022-10-08 18:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20944', '1', '30000411', '132456', '2022-10-08 18:45:22', '132456', '2022-10-08 18:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20944', '6', '3', '132456', '2022-10-08 18:45:22', '132456', '2022-10-08 18:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('209411', '1', '30000720', '132456', '2022-10-08 18:45:22', '132456', '2022-10-08 18:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20949', '5', 'No', '132456', '2022-10-08 18:45:22', '132456', '2022-10-08 18:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('209410', '5', 'No', '132456', '2022-10-08 18:45:22', '132456', '2022-10-08 18:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('209413', '209414', '1', '1', '0', '132456', '2022-10-08 18:45:22', '132456', '2022-10-08 18:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('209414', '20941', '0', '1', '0', '132456', '2022-10-08 18:45:22', '132456', '2022-10-08 18:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20941', '209415', '0', '1', '0', '132456', '2022-10-08 18:45:22', '132456', '2022-10-08 18:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('209415', '209416', '0', '1', '0', '132456', '2022-10-08 18:45:22', '132456', '2022-10-08 18:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('209416', '209417', '0', '1', '0', '132456', '2022-10-08 18:45:22', '132456', '2022-10-08 18:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('209417', '20942', '0', '1', '0', '132456', '2022-10-08 18:45:22', '132456', '2022-10-08 18:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20942', '20943', '0', '1', '0', '132456', '2022-10-08 18:45:22', '132456', '2022-10-08 18:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20943', '20944', '0', '1', '0', '132456', '2022-10-08 18:45:22', '132456', '2022-10-08 18:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20944', '209411', '0', '1', '0', '132456', '2022-10-08 18:45:22', '132456', '2022-10-08 18:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('209411', '20949', '0', '1', '0', '132456', '2022-10-08 18:45:22', '132456', '2022-10-08 18:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20949', '209410', '0', '1', '0', '132456', '2022-10-08 18:45:22', '132456', '2022-10-08 18:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2094', '13', '2094', NULL, 132456, '132456', '2022-10-08 18:45:22', '132456', '2022-10-08 18:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2094', '1', '132456', '132456', '2022-10-08 18:45:22', '132456', '2022-10-08 18:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2094', '2', 'dok', '132456', '2022-10-08 18:45:22', '132456', '2022-10-08 18:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2095', '1', '9', '132456', '2022-10-06 00:45:22', '132456', '2022-10-06 00:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2095', '3', '1', '132456', '2022-10-06 00:45:22', '132456', '2022-10-06 00:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2095', '4', '0', '132456', '2022-10-06 00:45:22', '132456', '2022-10-06 00:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2095', '6', 'QA Challenge #2095', '132456', '2022-10-06 00:45:22', '132456', '2022-10-06 00:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2095', '7', '1.0', '132456', '2022-10-06 00:45:22', '132456', '2022-10-06 00:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2095', '9', 'On', '132456', '2022-10-06 00:45:22', '132456', '2022-10-06 00:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2095', '10', 'On', '132456', '2022-10-06 00:45:22', '132456', '2022-10-06 00:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2095', '11', 'On', '132456', '2022-10-06 00:45:22', '132456', '2022-10-06 00:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2095', '12', 'Yes', '132456', '2022-10-06 00:45:22', '132456', '2022-10-06 00:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2095', '13', 'Yes', '132456', '2022-10-06 00:45:22', '132456', '2022-10-06 00:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2095', '14', 'Open', '132456', '2022-10-06 00:45:22', '132456', '2022-10-06 00:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2095', '16', '0.0', '132456', '2022-10-06 00:45:22', '132456', '2022-10-06 00:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2095', '26', 'On', '132456', '2022-10-06 00:45:22', '132456', '2022-10-06 00:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2095', '29', 'On', '132456', '2022-10-06 00:45:22', '132456', '2022-10-06 00:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2095', '42', 'true', '132456', '2022-10-06 00:45:22', '132456', '2022-10-06 00:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2095', '43', 'true', '132456', '2022-10-06 00:45:22', '132456', '2022-10-06 00:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20951', '2095', '1', '3', '2022-10-06 00:45:22', '2022-10-06 00:45:22', '2022-10-08 00:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-06 00:45:22', '132456', '2022-10-06 00:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20952', '2095', '2', '3', '2022-10-06 00:45:22', '2022-10-06 00:45:22', '2022-10-08 00:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-06 00:45:22', '132456', '2022-10-06 00:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20954', '2095', '4', '3', '2022-10-08 00:45:22', '2022-10-08 00:45:22', '2022-10-09 00:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-06 00:45:22', '132456', '2022-10-06 00:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20951', '2', '0', '132456', '2022-10-06 00:45:22', '132456', '2022-10-06 00:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20951', '5', 'No', '132456', '2022-10-06 00:45:22', '132456', '2022-10-06 00:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20952', '3', '0', '132456', '2022-10-06 00:45:22', '132456', '2022-10-06 00:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20952', '5', 'No', '132456', '2022-10-06 00:45:22', '132456', '2022-10-06 00:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20954', '5', 'No', '132456', '2022-10-06 00:45:22', '132456', '2022-10-06 00:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20954', '1', '30000413', '132456', '2022-10-06 00:45:22', '132456', '2022-10-06 00:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20951', '20952', '1', '1', '0', '132456', '2022-10-06 00:45:22', '132456', '2022-10-06 00:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20952', '20954', '0', '1', '0', '132456', '2022-10-06 00:45:22', '132456', '2022-10-06 00:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2095', '13', '2095', NULL, 132456, '132456', '2022-10-06 00:45:22', '132456', '2022-10-06 00:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2095', '1', '132456', '132456', '2022-10-06 00:45:22', '132456', '2022-10-06 00:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2095', '2', 'dok', '132456', '2022-10-06 00:45:22', '132456', '2022-10-06 00:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2096', '1', '37', '132456', '2022-10-05 09:45:22', '132456', '2022-10-05 09:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2096', '3', '1', '132456', '2022-10-05 09:45:22', '132456', '2022-10-05 09:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2096', '4', '0', '132456', '2022-10-05 09:45:22', '132456', '2022-10-05 09:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2096', '6', 'Data Science Challenge #2096', '132456', '2022-10-05 09:45:22', '132456', '2022-10-05 09:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2096', '7', '1.0', '132456', '2022-10-05 09:45:22', '132456', '2022-10-05 09:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2096', '9', 'On', '132456', '2022-10-05 09:45:22', '132456', '2022-10-05 09:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2096', '10', 'On', '132456', '2022-10-05 09:45:22', '132456', '2022-10-05 09:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2096', '11', 'On', '132456', '2022-10-05 09:45:22', '132456', '2022-10-05 09:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2096', '12', 'Yes', '132456', '2022-10-05 09:45:22', '132456', '2022-10-05 09:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2096', '13', 'Yes', '132456', '2022-10-05 09:45:22', '132456', '2022-10-05 09:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2096', '14', 'Open', '132456', '2022-10-05 09:45:22', '132456', '2022-10-05 09:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2096', '16', '0.0', '132456', '2022-10-05 09:45:22', '132456', '2022-10-05 09:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2096', '26', 'On', '132456', '2022-10-05 09:45:22', '132456', '2022-10-05 09:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2096', '29', 'On', '132456', '2022-10-05 09:45:22', '132456', '2022-10-05 09:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2096', '42', 'true', '132456', '2022-10-05 09:45:22', '132456', '2022-10-05 09:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2096', '43', 'true', '132456', '2022-10-05 09:45:22', '132456', '2022-10-05 09:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20961', '2096', '1', '3', '2022-10-05 09:45:22', '2022-10-05 09:45:22', '2022-10-07 09:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-05 09:45:22', '132456', '2022-10-05 09:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20962', '2096', '2', '3', '2022-10-05 09:45:22', '2022-10-05 09:45:22', '2022-10-07 09:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-05 09:45:22', '132456', '2022-10-05 09:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20964', '2096', '4', '3', '2022-10-07 09:45:22', '2022-10-07 09:45:22', '2022-10-08 09:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-05 09:45:22', '132456', '2022-10-05 09:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20961', '2', '0', '132456', '2022-10-05 09:45:22', '132456', '2022-10-05 09:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20961', '5', 'No', '132456', '2022-10-05 09:45:22', '132456', '2022-10-05 09:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20962', '3', '0', '132456', '2022-10-05 09:45:22', '132456', '2022-10-05 09:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20962', '5', 'No', '132456', '2022-10-05 09:45:22', '132456', '2022-10-05 09:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20964', '5', 'No', '132456', '2022-10-05 09:45:22', '132456', '2022-10-05 09:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20964', '1', '30000413', '132456', '2022-10-05 09:45:22', '132456', '2022-10-05 09:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20961', '20962', '1', '1', '0', '132456', '2022-10-05 09:45:22', '132456', '2022-10-05 09:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20962', '20964', '0', '1', '0', '132456', '2022-10-05 09:45:22', '132456', '2022-10-05 09:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2096', '13', '2096', NULL, 132456, '132456', '2022-10-05 09:45:22', '132456', '2022-10-05 09:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2096', '1', '132456', '132456', '2022-10-05 09:45:22', '132456', '2022-10-05 09:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2096', '2', 'dok', '132456', '2022-10-05 09:45:22', '132456', '2022-10-05 09:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2097', '1', '17', '132456', '2022-10-07 14:45:22', '132456', '2022-10-07 14:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2097', '3', '1', '132456', '2022-10-07 14:45:22', '132456', '2022-10-07 14:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2097', '4', '0', '132456', '2022-10-07 14:45:22', '132456', '2022-10-07 14:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2097', '6', 'Design 2 Round Challenge #2097', '132456', '2022-10-07 14:45:22', '132456', '2022-10-07 14:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2097', '7', '1.0', '132456', '2022-10-07 14:45:22', '132456', '2022-10-07 14:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2097', '9', 'On', '132456', '2022-10-07 14:45:22', '132456', '2022-10-07 14:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2097', '10', 'On', '132456', '2022-10-07 14:45:22', '132456', '2022-10-07 14:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2097', '11', 'On', '132456', '2022-10-07 14:45:22', '132456', '2022-10-07 14:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2097', '12', 'Yes', '132456', '2022-10-07 14:45:22', '132456', '2022-10-07 14:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2097', '13', 'Yes', '132456', '2022-10-07 14:45:22', '132456', '2022-10-07 14:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2097', '14', 'Open', '132456', '2022-10-07 14:45:22', '132456', '2022-10-07 14:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2097', '16', '0.0', '132456', '2022-10-07 14:45:22', '132456', '2022-10-07 14:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2097', '26', 'On', '132456', '2022-10-07 14:45:22', '132456', '2022-10-07 14:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2097', '29', 'On', '132456', '2022-10-07 14:45:22', '132456', '2022-10-07 14:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2097', '42', 'true', '132456', '2022-10-07 14:45:22', '132456', '2022-10-07 14:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2097', '43', 'true', '132456', '2022-10-07 14:45:22', '132456', '2022-10-07 14:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('209713', '2097', '13', '3', '2022-10-07 14:45:22', '2022-10-07 14:45:22', '2022-10-07 14:45:22', NULL, NULL, '0.0', '132456', '2022-10-07 14:45:22', '132456', '2022-10-07 14:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('209714', '2097', '14', '3', '2022-10-07 14:45:22', '2022-10-07 14:45:22', '2022-10-07 15:45:22', NULL, NULL, '3600000.0', '132456', '2022-10-07 14:45:22', '132456', '2022-10-07 14:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20971', '2097', '1', '3', '2022-10-07 15:45:22', '2022-10-07 15:45:22', '2022-10-09 15:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-07 14:45:22', '132456', '2022-10-07 14:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('209715', '2097', '15', '3', '2022-10-07 15:45:22', '2022-10-07 15:45:22', '2022-10-09 15:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-07 14:45:22', '132456', '2022-10-07 14:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('209716', '2097', '16', '3', '2022-10-09 15:45:22', '2022-10-09 15:45:22', '2022-10-09 21:45:22', NULL, NULL, '21600000.0', '132456', '2022-10-07 14:45:22', '132456', '2022-10-07 14:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('209717', '2097', '17', '3', '2022-10-09 21:45:22', '2022-10-09 21:45:22', '2022-10-10 21:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-07 14:45:22', '132456', '2022-10-07 14:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20972', '2097', '2', '2', '2022-10-07 15:45:22', '2022-10-07 15:45:22', '2022-10-11 15:45:22', NULL, NULL, '345600000.0', '132456', '2022-10-07 14:45:22', '132456', '2022-10-07 14:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20973', '2097', '3', '1', '2022-10-11 15:45:22', '2022-10-11 15:45:22', '2022-10-12 03:45:22', NULL, NULL, '43200000.0', '132456', '2022-10-07 14:45:22', '132456', '2022-10-07 14:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20974', '2097', '4', '1', '2022-10-12 03:45:22', '2022-10-12 03:45:22', '2022-10-12 15:45:22', NULL, NULL, '43200000.0', '132456', '2022-10-07 14:45:22', '132456', '2022-10-07 14:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('209711', '2097', '11', '1', '2022-10-12 15:45:22', '2022-10-12 15:45:22', '2022-10-13 03:45:22', NULL, NULL, '43200000.0', '132456', '2022-10-07 14:45:22', '132456', '2022-10-07 14:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20979', '2097', '9', '1', '2022-10-13 03:45:22', '2022-10-13 03:45:22', '2022-10-14 03:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-07 14:45:22', '132456', '2022-10-07 14:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('209710', '2097', '10', '1', '2022-10-14 03:45:22', '2022-10-14 03:45:22', '2022-10-14 09:45:22', NULL, NULL, '21600000.0', '132456', '2022-10-07 14:45:22', '132456', '2022-10-07 14:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('209713', '3', '0', '132456', '2022-10-07 14:45:22', '132456', '2022-10-07 14:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('209713', '5', 'No', '132456', '2022-10-07 14:45:22', '132456', '2022-10-07 14:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('209714', '1', '30000722', '132456', '2022-10-07 14:45:22', '132456', '2022-10-07 14:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('209714', '6', '3', '132456', '2022-10-07 14:45:22', '132456', '2022-10-07 14:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20971', '2', '0', '132456', '2022-10-07 14:45:22', '132456', '2022-10-07 14:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20971', '5', 'No', '132456', '2022-10-07 14:45:22', '132456', '2022-10-07 14:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('209715', '3', '0', '132456', '2022-10-07 14:45:22', '132456', '2022-10-07 14:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('209715', '5', 'No', '132456', '2022-10-07 14:45:22', '132456', '2022-10-07 14:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('209716', '1', '30000416', '132456', '2022-10-07 14:45:22', '132456', '2022-10-07 14:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('209717', '1', '30000417', '132456', '2022-10-07 14:45:22', '132456', '2022-10-07 14:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('209717', '6', '3', '132456', '2022-10-07 14:45:22', '132456', '2022-10-07 14:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20972', '3', '0', '132456', '2022-10-07 14:45:22', '132456', '2022-10-07 14:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20972', '5', 'No', '132456', '2022-10-07 14:45:22', '132456', '2022-10-07 14:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20973', '1', '30000410', '132456', '2022-10-07 14:45:22', '132456', '2022-10-07 14:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20974', '5', 'No', '132456', '2022-10-07 14:45:22', '132456', '2022-10-07 14:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20974', '1', '30000411', '132456', '2022-10-07 14:45:22', '132456', '2022-10-07 14:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20974', '6', '3', '132456', '2022-10-07 14:45:22', '132456', '2022-10-07 14:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('209711', '1', '30000720', '132456', '2022-10-07 14:45:22', '132456', '2022-10-07 14:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20979', '5', 'No', '132456', '2022-10-07 14:45:22', '132456', '2022-10-07 14:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('209710', '5', 'No', '132456', '2022-10-07 14:45:22', '132456', '2022-10-07 14:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('209713', '209714', '1', '1', '0', '132456', '2022-10-07 14:45:22', '132456', '2022-10-07 14:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('209714', '20971', '0', '1', '0', '132456', '2022-10-07 14:45:22', '132456', '2022-10-07 14:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20971', '209715', '0', '1', '0', '132456', '2022-10-07 14:45:22', '132456', '2022-10-07 14:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('209715', '209716', '0', '1', '0', '132456', '2022-10-07 14:45:22', '132456', '2022-10-07 14:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('209716', '209717', '0', '1', '0', '132456', '2022-10-07 14:45:22', '132456', '2022-10-07 14:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('209717', '20972', '0', '1', '0', '132456', '2022-10-07 14:45:22', '132456', '2022-10-07 14:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20972', '20973', '0', '1', '0', '132456', '2022-10-07 14:45:22', '132456', '2022-10-07 14:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20973', '20974', '0', '1', '0', '132456', '2022-10-07 14:45:22', '132456', '2022-10-07 14:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20974', '209711', '0', '1', '0', '132456', '2022-10-07 14:45:22', '132456', '2022-10-07 14:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('209711', '20979', '0', '1', '0', '132456', '2022-10-07 14:45:22', '132456', '2022-10-07 14:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20979', '209710', '0', '1', '0', '132456', '2022-10-07 14:45:22', '132456', '2022-10-07 14:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2097', '13', '2097', NULL, 132456, '132456', '2022-10-07 14:45:22', '132456', '2022-10-07 14:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2097', '1', '132456', '132456', '2022-10-07 14:45:22', '132456', '2022-10-07 14:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2097', '2', 'dok', '132456', '2022-10-07 14:45:22', '132456', '2022-10-07 14:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2098', '1', '37', '132456', '2022-10-08 07:45:22', '132456', '2022-10-08 07:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2098', '3', '1', '132456', '2022-10-08 07:45:22', '132456', '2022-10-08 07:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2098', '4', '0', '132456', '2022-10-08 07:45:22', '132456', '2022-10-08 07:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2098', '6', 'Data Science Challenge #2098', '132456', '2022-10-08 07:45:22', '132456', '2022-10-08 07:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2098', '7', '1.0', '132456', '2022-10-08 07:45:22', '132456', '2022-10-08 07:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2098', '9', 'On', '132456', '2022-10-08 07:45:22', '132456', '2022-10-08 07:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2098', '10', 'On', '132456', '2022-10-08 07:45:22', '132456', '2022-10-08 07:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2098', '11', 'On', '132456', '2022-10-08 07:45:22', '132456', '2022-10-08 07:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2098', '12', 'Yes', '132456', '2022-10-08 07:45:22', '132456', '2022-10-08 07:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2098', '13', 'Yes', '132456', '2022-10-08 07:45:22', '132456', '2022-10-08 07:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2098', '14', 'Open', '132456', '2022-10-08 07:45:22', '132456', '2022-10-08 07:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2098', '16', '0.0', '132456', '2022-10-08 07:45:22', '132456', '2022-10-08 07:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2098', '26', 'On', '132456', '2022-10-08 07:45:22', '132456', '2022-10-08 07:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2098', '29', 'On', '132456', '2022-10-08 07:45:22', '132456', '2022-10-08 07:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2098', '42', 'true', '132456', '2022-10-08 07:45:22', '132456', '2022-10-08 07:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2098', '43', 'true', '132456', '2022-10-08 07:45:22', '132456', '2022-10-08 07:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20981', '2098', '1', '3', '2022-10-08 07:45:22', '2022-10-08 07:45:22', '2022-10-10 07:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-08 07:45:22', '132456', '2022-10-08 07:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20982', '2098', '2', '3', '2022-10-08 07:45:22', '2022-10-08 07:45:22', '2022-10-10 07:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-08 07:45:22', '132456', '2022-10-08 07:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20984', '2098', '4', '2', '2022-10-10 07:45:22', '2022-10-10 07:45:22', '2022-10-11 07:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-08 07:45:22', '132456', '2022-10-08 07:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20981', '2', '0', '132456', '2022-10-08 07:45:22', '132456', '2022-10-08 07:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20981', '5', 'No', '132456', '2022-10-08 07:45:22', '132456', '2022-10-08 07:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20982', '3', '0', '132456', '2022-10-08 07:45:22', '132456', '2022-10-08 07:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20982', '5', 'No', '132456', '2022-10-08 07:45:22', '132456', '2022-10-08 07:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20984', '5', 'No', '132456', '2022-10-08 07:45:22', '132456', '2022-10-08 07:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20984', '1', '30000413', '132456', '2022-10-08 07:45:22', '132456', '2022-10-08 07:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20981', '20982', '1', '1', '0', '132456', '2022-10-08 07:45:22', '132456', '2022-10-08 07:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20982', '20984', '0', '1', '0', '132456', '2022-10-08 07:45:22', '132456', '2022-10-08 07:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2098', '13', '2098', NULL, 132456, '132456', '2022-10-08 07:45:22', '132456', '2022-10-08 07:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2098', '1', '132456', '132456', '2022-10-08 07:45:22', '132456', '2022-10-08 07:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2098', '2', 'dok', '132456', '2022-10-08 07:45:22', '132456', '2022-10-08 07:45:22');

-- Data for project
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2099', '1', '17', '132456', '2022-10-09 12:45:22', '132456', '2022-10-09 12:45:22');
-- Data for project_info
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2099', '3', '1', '132456', '2022-10-09 12:45:22', '132456', '2022-10-09 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2099', '4', '0', '132456', '2022-10-09 12:45:22', '132456', '2022-10-09 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2099', '6', 'Design 2 Round Challenge #2099', '132456', '2022-10-09 12:45:22', '132456', '2022-10-09 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2099', '7', '1.0', '132456', '2022-10-09 12:45:22', '132456', '2022-10-09 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2099', '9', 'On', '132456', '2022-10-09 12:45:22', '132456', '2022-10-09 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2099', '10', 'On', '132456', '2022-10-09 12:45:22', '132456', '2022-10-09 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2099', '11', 'On', '132456', '2022-10-09 12:45:22', '132456', '2022-10-09 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2099', '12', 'Yes', '132456', '2022-10-09 12:45:22', '132456', '2022-10-09 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2099', '13', 'Yes', '132456', '2022-10-09 12:45:22', '132456', '2022-10-09 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2099', '14', 'Open', '132456', '2022-10-09 12:45:22', '132456', '2022-10-09 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2099', '16', '0.0', '132456', '2022-10-09 12:45:22', '132456', '2022-10-09 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2099', '26', 'On', '132456', '2022-10-09 12:45:22', '132456', '2022-10-09 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2099', '29', 'On', '132456', '2022-10-09 12:45:22', '132456', '2022-10-09 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2099', '42', 'true', '132456', '2022-10-09 12:45:22', '132456', '2022-10-09 12:45:22');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2099', '43', 'true', '132456', '2022-10-09 12:45:22', '132456', '2022-10-09 12:45:22');
--  Data for project_phase
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('209913', '2099', '13', '3', '2022-10-09 12:45:22', '2022-10-09 12:45:22', '2022-10-09 12:45:22', NULL, NULL, '0.0', '132456', '2022-10-09 12:45:22', '132456', '2022-10-09 12:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('209914', '2099', '14', '3', '2022-10-09 12:45:22', '2022-10-09 12:45:22', '2022-10-09 13:45:22', NULL, NULL, '3600000.0', '132456', '2022-10-09 12:45:22', '132456', '2022-10-09 12:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20991', '2099', '1', '2', '2022-10-09 13:45:22', '2022-10-09 13:45:22', '2022-10-11 13:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-09 12:45:22', '132456', '2022-10-09 12:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('209915', '2099', '15', '2', '2022-10-09 13:45:22', '2022-10-09 13:45:22', '2022-10-11 13:45:22', NULL, NULL, '172800000.0', '132456', '2022-10-09 12:45:22', '132456', '2022-10-09 12:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('209916', '2099', '16', '1', '2022-10-11 13:45:22', '2022-10-11 13:45:22', '2022-10-11 19:45:22', NULL, NULL, '21600000.0', '132456', '2022-10-09 12:45:22', '132456', '2022-10-09 12:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('209917', '2099', '17', '1', '2022-10-11 19:45:22', '2022-10-11 19:45:22', '2022-10-12 19:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-09 12:45:22', '132456', '2022-10-09 12:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20992', '2099', '2', '2', '2022-10-09 13:45:22', '2022-10-09 13:45:22', '2022-10-13 13:45:22', NULL, NULL, '345600000.0', '132456', '2022-10-09 12:45:22', '132456', '2022-10-09 12:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20993', '2099', '3', '1', '2022-10-13 13:45:22', '2022-10-13 13:45:22', '2022-10-14 01:45:22', NULL, NULL, '43200000.0', '132456', '2022-10-09 12:45:22', '132456', '2022-10-09 12:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20994', '2099', '4', '1', '2022-10-14 01:45:22', '2022-10-14 01:45:22', '2022-10-14 13:45:22', NULL, NULL, '43200000.0', '132456', '2022-10-09 12:45:22', '132456', '2022-10-09 12:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('209911', '2099', '11', '1', '2022-10-14 13:45:22', '2022-10-14 13:45:22', '2022-10-15 01:45:22', NULL, NULL, '43200000.0', '132456', '2022-10-09 12:45:22', '132456', '2022-10-09 12:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('20999', '2099', '9', '1', '2022-10-15 01:45:22', '2022-10-15 01:45:22', '2022-10-16 01:45:22', NULL, NULL, '86400000.0', '132456', '2022-10-09 12:45:22', '132456', '2022-10-09 12:45:22');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('209910', '2099', '10', '1', '2022-10-16 01:45:22', '2022-10-16 01:45:22', '2022-10-16 07:45:22', NULL, NULL, '21600000.0', '132456', '2022-10-09 12:45:22', '132456', '2022-10-09 12:45:22');
--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('209913', '3', '0', '132456', '2022-10-09 12:45:22', '132456', '2022-10-09 12:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('209913', '5', 'No', '132456', '2022-10-09 12:45:22', '132456', '2022-10-09 12:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('209914', '1', '30000722', '132456', '2022-10-09 12:45:22', '132456', '2022-10-09 12:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('209914', '6', '3', '132456', '2022-10-09 12:45:22', '132456', '2022-10-09 12:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20991', '2', '0', '132456', '2022-10-09 12:45:22', '132456', '2022-10-09 12:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20991', '5', 'No', '132456', '2022-10-09 12:45:22', '132456', '2022-10-09 12:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('209915', '3', '0', '132456', '2022-10-09 12:45:22', '132456', '2022-10-09 12:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('209915', '5', 'No', '132456', '2022-10-09 12:45:22', '132456', '2022-10-09 12:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('209916', '1', '30000416', '132456', '2022-10-09 12:45:22', '132456', '2022-10-09 12:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('209917', '1', '30000417', '132456', '2022-10-09 12:45:22', '132456', '2022-10-09 12:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('209917', '6', '3', '132456', '2022-10-09 12:45:22', '132456', '2022-10-09 12:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20992', '3', '0', '132456', '2022-10-09 12:45:22', '132456', '2022-10-09 12:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20992', '5', 'No', '132456', '2022-10-09 12:45:22', '132456', '2022-10-09 12:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20993', '1', '30000410', '132456', '2022-10-09 12:45:22', '132456', '2022-10-09 12:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20994', '5', 'No', '132456', '2022-10-09 12:45:22', '132456', '2022-10-09 12:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20994', '1', '30000411', '132456', '2022-10-09 12:45:22', '132456', '2022-10-09 12:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20994', '6', '3', '132456', '2022-10-09 12:45:22', '132456', '2022-10-09 12:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('209911', '1', '30000720', '132456', '2022-10-09 12:45:22', '132456', '2022-10-09 12:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('20999', '5', 'No', '132456', '2022-10-09 12:45:22', '132456', '2022-10-09 12:45:22');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('209910', '5', 'No', '132456', '2022-10-09 12:45:22', '132456', '2022-10-09 12:45:22');
--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('209913', '209914', '1', '1', '0', '132456', '2022-10-09 12:45:22', '132456', '2022-10-09 12:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('209914', '20991', '0', '1', '0', '132456', '2022-10-09 12:45:22', '132456', '2022-10-09 12:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20991', '209915', '0', '1', '0', '132456', '2022-10-09 12:45:22', '132456', '2022-10-09 12:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('209915', '209916', '0', '1', '0', '132456', '2022-10-09 12:45:22', '132456', '2022-10-09 12:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('209916', '209917', '0', '1', '0', '132456', '2022-10-09 12:45:22', '132456', '2022-10-09 12:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('209917', '20992', '0', '1', '0', '132456', '2022-10-09 12:45:22', '132456', '2022-10-09 12:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20992', '20993', '0', '1', '0', '132456', '2022-10-09 12:45:22', '132456', '2022-10-09 12:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20993', '20994', '0', '1', '0', '132456', '2022-10-09 12:45:22', '132456', '2022-10-09 12:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20994', '209911', '0', '1', '0', '132456', '2022-10-09 12:45:22', '132456', '2022-10-09 12:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('209911', '20999', '0', '1', '0', '132456', '2022-10-09 12:45:22', '132456', '2022-10-09 12:45:22');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('20999', '209910', '0', '1', '0', '132456', '2022-10-09 12:45:22', '132456', '2022-10-09 12:45:22');
--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('2099', '13', '2099', NULL, 132456, '132456', '2022-10-09 12:45:22', '132456', '2022-10-09 12:45:22');
--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2099', '1', '132456', '132456', '2022-10-09 12:45:22', '132456', '2022-10-09 12:45:22');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('2099', '2', 'dok', '132456', '2022-10-09 12:45:22', '132456', '2022-10-09 12:45:22');

INSERT INTO upload (upload_id, project_id, project_phase_id, resource_id, upload_type_id, upload_status_id, parameter, create_user, create_date, modify_user, modify_date)
VALUES ("1", "2000", "20001", "1", 1, 1, 'no_file.txt', "132456", '2022-10-13 14:00:10', "132456", '2022-10-13 14:00:10');
INSERT INTO submission (submission_id, upload_id, submission_status_id, screening_score, initial_score, final_score, placement, submission_type_id, create_user, create_date, modify_user, modify_date)
VALUES ("10000", "1", 1, NULL, NULL, NULL, NULL, "1", "132456", '2022-10-13 14:00:10', "132456", '2022-10-13 14:00:10');
INSERT INTO resource_submission (resource_id, submission_id, create_user, create_date, modify_user, modify_date)
VALUES ("1", "10000", "132456", '2022-10-13 14:00:10', "132456", '2022-10-13 14:00:10');

INSERT INTO tcs_catalog:informix.prize (prize_id, project_id, place, prize_amount, prize_type_id, number_of_submissions, create_user, create_date, modify_user, modify_date) VALUES(1, 2099, 1, 50.0, 14, 1, 'dok', '2022-10-12 12:14:40.000', 'dok', '2022-10-12 12:14:40.000'); INSERT INTO tcs_catalog:informix.prize (prize_id, project_id, place, prize_amount, prize_type_id, number_of_submissions, create_user, create_date, modify_user, modify_date) VALUES(2, 2099, 1, 1000.0, 15, 1, 'dok', '2022-10-12 12:14:40.000', 'dok', '2022-10-12 12:14:40.000'); INSERT INTO tcs_catalog:informix.prize (prize_id, project_id, place, prize_amount, prize_type_id, number_of_submissions, create_user, create_date, modify_user, modify_date) VALUES(3, 2099, 2, 500.0, 15, 1, 'dok', '2022-10-13 12:14:40.000', 'dok', '2022-10-13 12:14:40.000'); INSERT INTO tcs_catalog:informix.prize (prize_id, project_id, place, prize_amount, prize_type_id, number_of_submissions, create_user, create_date, modify_user, modify_date) VALUES(4, 2099, 3, 250.0, 15, 1, 'dok', '2022-10-13 12:14:40.000', 'dok', '2022-10-13 12:14:40.000'); INSERT INTO tcs_catalog:informix.prize (prize_id, project_id, place, prize_amount, prize_type_id, number_of_submissions, create_user, create_date, modify_user, modify_date) VALUES(5, 2099, 1, 50.0, 14, 1, 'dok', '2022-10-13 12:14:40.000', 'dok', '2022-10-13 12:14:40.000'); INSERT INTO tcs_catalog:informix.prize (prize_id, project_id, place, prize_amount, prize_type_id, number_of_submissions, create_user, create_date, modify_user, modify_date) VALUES(6, 2099, 1, 50.0, 14, 1, 'dok', '2022-10-13 12:14:40.000', 'dok', '2022-10-13 12:14:40.000'); INSERT INTO tcs_catalog:informix.prize (prize_id, project_id, place, prize_amount, prize_type_id, number_of_submissions, create_user, create_date, modify_user, modify_date) VALUES(7, 2099, 1, 50.0, 14, 1, 'dok', '2022-10-13 12:14:40.000', 'dok', '2022-10-13 12:14:40.000'); INSERT INTO tcs_catalog:informix.prize (prize_id, project_id, place, prize_amount, prize_type_id, number_of_submissions, create_user, create_date, modify_user, modify_date) VALUES(8, 2099, 1, 50.0, 14, 1, 'dok', '2022-10-13 12:14:40.000', 'dok', '2022-10-13 12:14:40.000');

INSERT INTO prize (prize_id, project_id, place, prize_amount, prize_type_id, number_of_submissions, create_user, create_date, modify_user, modify_date)
VALUES('20291', '2029', '1', '1000', '15', '1', '132456', '2022-10-16 08:03:51', '132456', '2022-10-16 08:03:51');
INSERT INTO prize (prize_id, project_id, place, prize_amount, prize_type_id, number_of_submissions, create_user, create_date, modify_user, modify_date)
VALUES('20292', '2029', '2', '500', '15', '1', '132456', '2022-10-16 08:03:51', '132456', '2022-10-16 08:03:51');
INSERT INTO prize (prize_id, project_id, place, prize_amount, prize_type_id, number_of_submissions, create_user, create_date, modify_user, modify_date)
VALUES('20293', '2029', '3', '250', '15', '1', '132456', '2022-10-16 08:03:51', '132456', '2022-10-16 08:03:51');
INSERT INTO prize (prize_id, project_id, place, prize_amount, prize_type_id, number_of_submissions, create_user, create_date, modify_user, modify_date)
VALUES('20294', '2029', '1', '50', '14', '1', '132456', '2022-10-16 08:03:51', '132456', '2022-10-16 08:03:51');
INSERT INTO prize (prize_id, project_id, place, prize_amount, prize_type_id, number_of_submissions, create_user, create_date, modify_user, modify_date)
VALUES('20295', '2029', '2', '50', '14', '1', '132456', '2022-10-16 08:03:51', '132456', '2022-10-16 08:03:51');
INSERT INTO prize (prize_id, project_id, place, prize_amount, prize_type_id, number_of_submissions, create_user, create_date, modify_user, modify_date)
VALUES('20296', '2029', '3', '50', '14', '1', '132456', '2022-10-16 08:03:51', '132456', '2022-10-16 08:03:51');
INSERT INTO prize (prize_id, project_id, place, prize_amount, prize_type_id, number_of_submissions, create_user, create_date, modify_user, modify_date)
VALUES('20297', '2029', '4', '50', '14', '1', '132456', '2022-10-16 08:03:51', '132456', '2022-10-16 08:03:51');
INSERT INTO prize (prize_id, project_id, place, prize_amount, prize_type_id, number_of_submissions, create_user, create_date, modify_user, modify_date)
VALUES('20298', '2029', '5', '50', '14', '1', '132456', '2022-10-16 08:03:51', '132456', '2022-10-16 08:03:51');
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
VALUES ('20291', '1', '2029', NULL, 124764, '124764', '2022-10-16 08:03:51', '124764', '2022-10-16 08:03:51');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
VALUES ('20291', '1', '124764', '124764', '2022-10-16 08:03:51', '124764', '2022-10-16 08:03:51');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
VALUES ('20291', '2', 'Hung', '124764', '2022-10-16 08:03:51', '124764', '2022-10-16 08:03:51');
INSERT INTO upload (upload_id, project_id, project_phase_id, resource_id, upload_type_id, upload_status_id, parameter, create_user, create_date, modify_user, modify_date)
VALUES ("20291", "2029", "20292", "20291", 1, 1, 'no_file.txt', "124764", '2022-10-16 08:03:51', "124764", '2022-10-16 08:03:51');
INSERT INTO submission (submission_id, upload_id, submission_status_id, screening_score, initial_score, final_score, placement, submission_type_id, create_user, create_date, modify_user, modify_date)
VALUES ("20291", "20291", 1, NULL, NULL, 100, 1, "1", "124764", '2022-10-16 08:03:51', "124764", '2022-10-16 08:03:51');
INSERT INTO resource_submission (resource_id, submission_id, create_user, create_date, modify_user, modify_date)
VALUES ("20291", "20291", "124764", '2022-10-16 08:03:51', "124764", '2022-10-16 08:03:51');
INSERT INTO upload (upload_id, project_id, project_phase_id, resource_id, upload_type_id, upload_status_id, parameter, create_user, create_date, modify_user, modify_date)
VALUES ("20292", "2029", "202915", "20291", 1, 1, 'no_file.txt', "124764", '2022-10-16 08:03:51', "124764", '2022-10-16 08:03:51');
INSERT INTO submission (submission_id, upload_id, submission_status_id, screening_score, initial_score, final_score, placement, submission_type_id, create_user, create_date, modify_user, modify_date)
VALUES ("20292", "20292", 1, NULL, NULL, 100, 1, "3", "124764", '2022-10-16 08:03:51', "124764", '2022-10-16 08:03:51');
INSERT INTO resource_submission (resource_id, submission_id, create_user, create_date, modify_user, modify_date)
VALUES ("20291", "20292", "124764", '2022-10-16 08:03:51', "124764", '2022-10-16 08:03:51');
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
VALUES ('20292', '1', '2029', NULL, 124772, '124772', '2022-10-16 08:03:51', '124772', '2022-10-16 08:03:51');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
VALUES ('20292', '1', '124772', '124772', '2022-10-16 08:03:51', '124772', '2022-10-16 08:03:51');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
VALUES ('20292', '2', 'Partha', '124772', '2022-10-16 08:03:51', '124772', '2022-10-16 08:03:51');
INSERT INTO upload (upload_id, project_id, project_phase_id, resource_id, upload_type_id, upload_status_id, parameter, create_user, create_date, modify_user, modify_date)
VALUES ("20293", "2029", "20292", "20292", 1, 1, 'no_file.txt', "124772", '2022-10-16 08:03:51', "124772", '2022-10-16 08:03:51');
INSERT INTO submission (submission_id, upload_id, submission_status_id, screening_score, initial_score, final_score, placement, submission_type_id, create_user, create_date, modify_user, modify_date)
VALUES ("20293", "20293", 1, NULL, NULL, 100, 2, "1", "124772", '2022-10-16 08:03:51', "124772", '2022-10-16 08:03:51');
INSERT INTO resource_submission (resource_id, submission_id, create_user, create_date, modify_user, modify_date)
VALUES ("20292", "20293", "124772", '2022-10-16 08:03:51', "124772", '2022-10-16 08:03:51');
INSERT INTO upload (upload_id, project_id, project_phase_id, resource_id, upload_type_id, upload_status_id, parameter, create_user, create_date, modify_user, modify_date)
VALUES ("20294", "2029", "202915", "20292", 1, 1, 'no_file.txt', "124772", '2022-10-16 08:03:51', "124772", '2022-10-16 08:03:51');
INSERT INTO submission (submission_id, upload_id, submission_status_id, screening_score, initial_score, final_score, placement, submission_type_id, create_user, create_date, modify_user, modify_date)
VALUES ("20294", "20294", 1, NULL, NULL, 100, 2, "3", "124772", '2022-10-16 08:03:51', "124772", '2022-10-16 08:03:51');
INSERT INTO resource_submission (resource_id, submission_id, create_user, create_date, modify_user, modify_date)
VALUES ("20292", "20294", "124772", '2022-10-16 08:03:51', "124772", '2022-10-16 08:03:51');
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
VALUES ('20293', '1', '2029', NULL, 124834, '124834', '2022-10-16 08:03:51', '124834', '2022-10-16 08:03:51');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
VALUES ('20293', '1', '124834', '124834', '2022-10-16 08:03:51', '124834', '2022-10-16 08:03:51');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
VALUES ('20293', '2', 'lightspeed', '124834', '2022-10-16 08:03:51', '124834', '2022-10-16 08:03:51');
INSERT INTO upload (upload_id, project_id, project_phase_id, resource_id, upload_type_id, upload_status_id, parameter, create_user, create_date, modify_user, modify_date)
VALUES ("20295", "2029", "20292", "20293", 1, 1, 'no_file.txt', "124834", '2022-10-16 08:03:51', "124834", '2022-10-16 08:03:51');
INSERT INTO submission (submission_id, upload_id, submission_status_id, screening_score, initial_score, final_score, placement, submission_type_id, create_user, create_date, modify_user, modify_date)
VALUES ("20295", "20295", 1, NULL, NULL, 100, 3, "1", "124834", '2022-10-16 08:03:51', "124834", '2022-10-16 08:03:51');
INSERT INTO resource_submission (resource_id, submission_id, create_user, create_date, modify_user, modify_date)
VALUES ("20293", "20295", "124834", '2022-10-16 08:03:51', "124834", '2022-10-16 08:03:51');
INSERT INTO upload (upload_id, project_id, project_phase_id, resource_id, upload_type_id, upload_status_id, parameter, create_user, create_date, modify_user, modify_date)
VALUES ("20296", "2029", "202915", "20293", 1, 1, 'no_file.txt', "124834", '2022-10-16 08:03:51', "124834", '2022-10-16 08:03:51');
INSERT INTO submission (submission_id, upload_id, submission_status_id, screening_score, initial_score, final_score, placement, submission_type_id, create_user, create_date, modify_user, modify_date)
VALUES ("20296", "20296", 1, NULL, NULL, 100, 3, "3", "124834", '2022-10-16 08:03:51', "124834", '2022-10-16 08:03:51');
INSERT INTO resource_submission (resource_id, submission_id, create_user, create_date, modify_user, modify_date)
VALUES ("20293", "20296", "124834", '2022-10-16 08:03:51', "124834", '2022-10-16 08:03:51');
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
VALUES ('20294', '1', '2029', NULL, 124836, '124836', '2022-10-16 08:03:51', '124836', '2022-10-16 08:03:51');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
VALUES ('20294', '1', '124836', '124836', '2022-10-16 08:03:51', '124836', '2022-10-16 08:03:51');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
VALUES ('20294', '2', 'annej9ny', '124836', '2022-10-16 08:03:51', '124836', '2022-10-16 08:03:51');
INSERT INTO upload (upload_id, project_id, project_phase_id, resource_id, upload_type_id, upload_status_id, parameter, create_user, create_date, modify_user, modify_date)
VALUES ("20297", "2029", "20292", "20294", 1, 1, 'no_file.txt', "124836", '2022-10-16 08:03:51', "124836", '2022-10-16 08:03:51');
INSERT INTO submission (submission_id, upload_id, submission_status_id, screening_score, initial_score, final_score, placement, submission_type_id, create_user, create_date, modify_user, modify_date)
VALUES ("20297", "20297", 1, NULL, NULL, 90, 4, "1", "124836", '2022-10-16 08:03:51', "124836", '2022-10-16 08:03:51');
INSERT INTO resource_submission (resource_id, submission_id, create_user, create_date, modify_user, modify_date)
VALUES ("20294", "20297", "124836", '2022-10-16 08:03:51', "124836", '2022-10-16 08:03:51');
INSERT INTO upload (upload_id, project_id, project_phase_id, resource_id, upload_type_id, upload_status_id, parameter, create_user, create_date, modify_user, modify_date)
VALUES ("20298", "2029", "202915", "20294", 1, 1, 'no_file.txt', "124836", '2022-10-16 08:03:51', "124836", '2022-10-16 08:03:51');
INSERT INTO submission (submission_id, upload_id, submission_status_id, screening_score, initial_score, final_score, placement, submission_type_id, create_user, create_date, modify_user, modify_date)
VALUES ("20298", "20298", 1, NULL, NULL, 90, 4, "3", "124836", '2022-10-16 08:03:51', "124836", '2022-10-16 08:03:51');
INSERT INTO resource_submission (resource_id, submission_id, create_user, create_date, modify_user, modify_date)
VALUES ("20294", "20298", "124836", '2022-10-16 08:03:51', "124836", '2022-10-16 08:03:51');
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
VALUES ('20295', '1', '2029', NULL, 124852, '124852', '2022-10-16 08:03:51', '124852', '2022-10-16 08:03:51');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
VALUES ('20295', '1', '124852', '124852', '2022-10-16 08:03:51', '124852', '2022-10-16 08:03:51');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
VALUES ('20295', '2', 'plinehan', '124852', '2022-10-16 08:03:51', '124852', '2022-10-16 08:03:51');
INSERT INTO upload (upload_id, project_id, project_phase_id, resource_id, upload_type_id, upload_status_id, parameter, create_user, create_date, modify_user, modify_date)
VALUES ("20299", "2029", "20292", "20295", 1, 1, 'no_file.txt', "124852", '2022-10-16 08:03:51', "124852", '2022-10-16 08:03:51');
INSERT INTO submission (submission_id, upload_id, submission_status_id, screening_score, initial_score, final_score, placement, submission_type_id, create_user, create_date, modify_user, modify_date)
VALUES ("20299", "20299", 1, NULL, NULL, 80, 5, "1", "124852", '2022-10-16 08:03:51', "124852", '2022-10-16 08:03:51');
INSERT INTO resource_submission (resource_id, submission_id, create_user, create_date, modify_user, modify_date)
VALUES ("20295", "20299", "124852", '2022-10-16 08:03:51', "124852", '2022-10-16 08:03:51');
INSERT INTO upload (upload_id, project_id, project_phase_id, resource_id, upload_type_id, upload_status_id, parameter, create_user, create_date, modify_user, modify_date)
VALUES ("20300", "2029", "202915", "20295", 1, 1, 'no_file.txt', "124852", '2022-10-16 08:03:51', "124852", '2022-10-16 08:03:51');
INSERT INTO submission (submission_id, upload_id, submission_status_id, screening_score, initial_score, final_score, placement, submission_type_id, create_user, create_date, modify_user, modify_date)
VALUES ("20300", "20300", 1, NULL, NULL, 80, 5, "3", "124852", '2022-10-16 08:03:51', "124852", '2022-10-16 08:03:51');
INSERT INTO resource_submission (resource_id, submission_id, create_user, create_date, modify_user, modify_date)
VALUES ("20295", "20300", "124852", '2022-10-16 08:03:51', "124852", '2022-10-16 08:03:51');
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
VALUES ('20296', '1', '2029', NULL, 124856, '124856', '2022-10-16 08:03:51', '124856', '2022-10-16 08:03:51');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
VALUES ('20296', '1', '124856', '124856', '2022-10-16 08:03:51', '124856', '2022-10-16 08:03:51');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
VALUES ('20296', '2', 'wyzmo', '124856', '2022-10-16 08:03:51', '124856', '2022-10-16 08:03:51');
INSERT INTO upload (upload_id, project_id, project_phase_id, resource_id, upload_type_id, upload_status_id, parameter, create_user, create_date, modify_user, modify_date)
VALUES ("20301", "2029", "20292", "20296", 1, 1, 'no_file.txt', "124856", '2022-10-16 08:03:51', "124856", '2022-10-16 08:03:51');
INSERT INTO submission (submission_id, upload_id, submission_status_id, screening_score, initial_score, final_score, placement, submission_type_id, create_user, create_date, modify_user, modify_date)
VALUES ("20301", "20301", 1, NULL, NULL, 80, 6, "1", "124856", '2022-10-16 08:03:51', "124856", '2022-10-16 08:03:51');
INSERT INTO resource_submission (resource_id, submission_id, create_user, create_date, modify_user, modify_date)
VALUES ("20296", "20301", "124856", '2022-10-16 08:03:51', "124856", '2022-10-16 08:03:51');
INSERT INTO upload (upload_id, project_id, project_phase_id, resource_id, upload_type_id, upload_status_id, parameter, create_user, create_date, modify_user, modify_date)
VALUES ("20302", "2029", "202915", "20296", 1, 1, 'no_file.txt', "124856", '2022-10-16 08:03:51', "124856", '2022-10-16 08:03:51');
INSERT INTO submission (submission_id, upload_id, submission_status_id, screening_score, initial_score, final_score, placement, submission_type_id, create_user, create_date, modify_user, modify_date)
VALUES ("20302", "20302", 1, NULL, NULL, 80, 6, "3", "124856", '2022-10-16 08:03:51', "124856", '2022-10-16 08:03:51');
INSERT INTO resource_submission (resource_id, submission_id, create_user, create_date, modify_user, modify_date)
VALUES ("20296", "20302", "124856", '2022-10-16 08:03:51', "124856", '2022-10-16 08:03:51');
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
VALUES ('20297', '1', '2029', NULL, 124916, '124916', '2022-10-16 08:03:51', '124916', '2022-10-16 08:03:51');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
VALUES ('20297', '1', '124916', '124916', '2022-10-16 08:03:51', '124916', '2022-10-16 08:03:51');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
VALUES ('20297', '2', 'Yoshi', '124916', '2022-10-16 08:03:51', '124916', '2022-10-16 08:03:51');
INSERT INTO upload (upload_id, project_id, project_phase_id, resource_id, upload_type_id, upload_status_id, parameter, create_user, create_date, modify_user, modify_date)
VALUES ("20303", "2029", "20292", "20297", 1, 1, 'no_file.txt', "124916", '2022-10-16 08:03:51', "124916", '2022-10-16 08:03:51');
INSERT INTO submission (submission_id, upload_id, submission_status_id, screening_score, initial_score, final_score, placement, submission_type_id, create_user, create_date, modify_user, modify_date)
VALUES ("20303", "20303", 3, NULL, NULL, 60, 7, "1", "124916", '2022-10-16 08:03:51', "124916", '2022-10-16 08:03:51');
INSERT INTO resource_submission (resource_id, submission_id, create_user, create_date, modify_user, modify_date)
VALUES ("20297", "20303", "124916", '2022-10-16 08:03:51', "124916", '2022-10-16 08:03:51');
INSERT INTO upload (upload_id, project_id, project_phase_id, resource_id, upload_type_id, upload_status_id, parameter, create_user, create_date, modify_user, modify_date)
VALUES ("20304", "2029", "202915", "20297", 1, 1, 'no_file.txt', "124916", '2022-10-16 08:03:51', "124916", '2022-10-16 08:03:51');
INSERT INTO submission (submission_id, upload_id, submission_status_id, screening_score, initial_score, final_score, placement, submission_type_id, create_user, create_date, modify_user, modify_date)
VALUES ("20304", "20304", 3, NULL, NULL, 60, 7, "3", "124916", '2022-10-16 08:03:51', "124916", '2022-10-16 08:03:51');
INSERT INTO resource_submission (resource_id, submission_id, create_user, create_date, modify_user, modify_date)
VALUES ("20297", "20304", "124916", '2022-10-16 08:03:51', "124916", '2022-10-16 08:03:51');
