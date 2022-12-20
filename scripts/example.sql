-- How to edit track or category.

-- See query below
-- Edit project_category_id field to number desired. see Project Category Lookup below
-- For Dev Challenge, it usually use 39 (Code)
-- For Design Challenge, it may vary, but usually 17 (Web Design)
-- For QA, 9 (Bug Hunt)
-- For Data Science, 37 (Marathon Match)
-- For First2Finish, 38 for code, and 40 for design
-- For Task, I can't find any. Maybe it managed in different system?

-- Data for project
-- Project Status ID | 1 | Active
-- Project Category ID | 17 | Web Design
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('32000', '1', '17', '132458', '2022-09-29 13:41:31', '132458', '2022-09-29 13:41:31');


-- How to edit the active date
-- There are several phase in a project 
-- At minimum, it have Registration, Submission, Review
-- In each phase there are several field related to date:
-- fixed_start_time: start of phase
-- scheduled_start_time: same as fixed_start_time
-- scheduled_end_time: end of phase
-- duration: duration (end_time - start_time) 

-- The format of time is YYYY-MM-DD HH:MM:SS
-- And duration is in milliseconds

-- If time now is passing the phase then the will be close, field phase_status_id = 3 (Closed)
-- If time in between start time and end time of phase, field phase_status_id = 2 (Open)
-- If time is before start time of phase, field phase_status_id = 1 (Scheduled)

-- Data for project_phase
-- Phase Type ID | Name | Status
-- 1	Registration 	| Closed
-- 2	Submission		| Closed
-- 3	Screening 		| Closed
-- 4	Review			| Closed
-- 11	Approval		| Open
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('320001', '32000', '1', '3', '2022-09-29 13:41:31', '2022-09-29 13:41:31', '2022-10-01 13:41:31', NULL, NULL, '172800000.0', '132458', '2022-09-29 13:41:31', '132458', '2022-09-29 13:41:31');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('320002', '32000', '2', '3', '2022-09-29 13:41:31', '2022-09-29 13:41:31', '2022-10-01 13:41:31', NULL, NULL, '172800000.0', '132458', '2022-09-29 13:41:31', '132458', '2022-09-29 13:41:31');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('320003', '32000', '3', '3', '2022-10-01 13:41:31', '2022-10-01 13:41:31', '2022-10-02 13:41:31', NULL, NULL, '86400000.0', '132458', '2022-09-29 13:41:31', '132458', '2022-09-29 13:41:31');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('320004', '32000', '4', '3', '2022-10-02 13:41:31', '2022-10-02 13:41:31', '2022-10-03 01:41:31', NULL, NULL, '43200000.0', '132458', '2022-09-29 13:41:31', '132458', '2022-09-29 13:41:31');
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('3200011', '32000', '11', '2', '2022-10-03 01:41:31', '2022-10-03 01:41:31', '2022-10-03 07:41:31', NULL, NULL, '21600000.0', '132458', '2022-09-29 13:41:31', '132458', '2022-09-29 13:41:31');


-- Project Category Lookup
-- ID | Project Type ID | Name
-- Dev
-- 6	2	Specification
-- 7	2	Architecture
-- 8	2	Component Production
-- 9	2	Bug Hunt
-- 10	2	Deployment
-- 11	2	Security
-- 12	2	Process
-- 13	2	Test Suites
-- 14	2	Assembly Competition
-- 15	2	Legacy
-- 19	2	UI Prototypes
-- 23	2	Conceptualization
-- 24	2	RIA Build
-- 25	2	RIA Component
-- 26	2	Test Scenarios
-- 27	2	Spec Review
-- 29	2	Copilot Posting
-- 35	2	Content Creation
-- 36	2	Reporting
-- 37	2	Marathon Match
-- 38	2	First2Finish
-- 39	2	Code
-- Design
-- 16	3	Banners/Icons
-- 17	3	Web Design
-- 18	3	Wireframes
-- 20	3	Logo Design
-- 21	3	Print/Presentation
-- 22	3	Idea Generation
-- 30	3	Widget or Mobile Screen Design
-- 31	3	Front-End Flash
-- 32	3	Application Front-End Design
-- 34	3	Other
-- 40	3	Design First2Finish


-- Phase Type Lookup
-- ID | Name
-- 1	Registration
-- 2	Submission
-- 3	Screening
-- 4	Review
-- 5	Appeals
-- 6	Appeals Response
-- 7	Aggregation
-- 8	Aggregation Review
-- 9	Final Fix
-- 10	Final Review
-- 11	Approval
-- 12	Post-Mortem
-- 13	Specification Submission
-- 14	Specification Review
-- 15	Checkpoint Submission
-- 16	Checkpoint Screening
-- 17	Checkpoint Review
-- 18	Iterative Review

-- Phase Status Lookup
-- ID | Name
-- 1	Scheduled
-- 2	Open
-- 3	Closed



-- Data for project_info
-- Project Info Type ID | Name | Value
-- 3 Version ID | 1
-- 4 Developer Forum ID | 0
-- 6 Project Name | Design Challenge #32000
-- 7 Project Version | 1.0
-- 9 Autopilot Option | On
-- 10 Status Notification | On
-- 11 Timeline Notification | On
-- 12 Public | Yes
-- 13 Rated | Yes
-- 14 Eligibility | Open
-- 16 Payments | 0.0
-- 26 Digital Run Flag | On
-- 29 Contest Indicator | On
-- 41 Approval Required | true
-- 43 Send Winner Emails | true
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('32000', '3', '1', '132458', '2022-09-29 13:41:31', '132458', '2022-09-29 13:41:31');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('32000', '4', '0', '132458', '2022-09-29 13:41:31', '132458', '2022-09-29 13:41:31');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('32000', '6', 'Design Challenge #32000', '132458', '2022-09-29 13:41:31', '132458', '2022-09-29 13:41:31');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('32000', '7', '1.0', '132458', '2022-09-29 13:41:31', '132458', '2022-09-29 13:41:31');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('32000', '9', 'On', '132458', '2022-09-29 13:41:31', '132458', '2022-09-29 13:41:31');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('32000', '10', 'On', '132458', '2022-09-29 13:41:31', '132458', '2022-09-29 13:41:31');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('32000', '11', 'On', '132458', '2022-09-29 13:41:31', '132458', '2022-09-29 13:41:31');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('32000', '12', 'Yes', '132458', '2022-09-29 13:41:31', '132458', '2022-09-29 13:41:31');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('32000', '13', 'Yes', '132458', '2022-09-29 13:41:31', '132458', '2022-09-29 13:41:31');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('32000', '14', 'Open', '132458', '2022-09-29 13:41:31', '132458', '2022-09-29 13:41:31');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('32000', '16', '0.0', '132458', '2022-09-29 13:41:31', '132458', '2022-09-29 13:41:31');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('32000', '26', 'On', '132458', '2022-09-29 13:41:31', '132458', '2022-09-29 13:41:31');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('32000', '29', 'On', '132458', '2022-09-29 13:41:31', '132458', '2022-09-29 13:41:31');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('32000', '42', 'true', '132458', '2022-09-29 13:41:31', '132458', '2022-09-29 13:41:31');
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('32000', '43', 'true', '132458', '2022-09-29 13:41:31', '132458', '2022-09-29 13:41:31');

--  Data for phase_criteria
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('320001', '2', '0', '132458', '2022-09-29 13:41:31', '132458', '2022-09-29 13:41:31');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('320001', '5', 'No', '132458', '2022-09-29 13:41:31', '132458', '2022-09-29 13:41:31');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('320002', '3', '0', '132458', '2022-09-29 13:41:31', '132458', '2022-09-29 13:41:31');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('320002', '5', 'No', '132458', '2022-09-29 13:41:31', '132458', '2022-09-29 13:41:31');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('320003', '1', '30000410', '132458', '2022-09-29 13:41:31', '132458', '2022-09-29 13:41:31');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('320004', '5', 'No', '132458', '2022-09-29 13:41:31', '132458', '2022-09-29 13:41:31');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('320004', '1', '30000411', '132458', '2022-09-29 13:41:31', '132458', '2022-09-29 13:41:31');
INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('3200011', '1', '30000720', '132458', '2022-09-29 13:41:31', '132458', '2022-09-29 13:41:31');

--  Data for phase_dependency
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('320001', '320002', '1', '1', '0', '132458', '2022-09-29 13:41:31', '132458', '2022-09-29 13:41:31');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('320002', '320003', '0', '1', '0', '132458', '2022-09-29 13:41:31', '132458', '2022-09-29 13:41:31');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('320003', '320004', '0', '1', '0', '132458', '2022-09-29 13:41:31', '132458', '2022-09-29 13:41:31');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('320004', '3200011', '0', '1', '0', '132458', '2022-09-29 13:41:31', '132458', '2022-09-29 13:41:31');

--  Data for resource
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('32000', '13', '32000', NULL, 132458, '132458', '2022-09-29 13:41:31', '132458', '2022-09-29 13:41:31');

--  Data for resource_info
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('32000', '1', '132458', '132458', '2022-09-29 13:41:31', '132458', '2022-09-29 13:41:31');
INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('32000', '2', 'user', '132458', '2022-09-29 13:41:31', '132458', '2022-09-29 13:41:31');
