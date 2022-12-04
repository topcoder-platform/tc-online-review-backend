from datetime import datetime, timedelta
import random

init_project_id = 2000 # Change to avoid duplicate data to be inserted
total = 100 # Change to how many project

project_details = {
	'dev': {
		'name': 'Development',
		'project_category_id': 39, # Code
		'project_phases': {
			1: {
				'duration': 48, # in hour
				'criteria': [[2, '0'], [5, 'No']]
				}, # Register
			2: {
				'duration': 48,
				'criteria': [[3, '0'], [5, 'No']]
				}, # Submission
			4: {
				'duration': 24,
				'criteria': [[5, 'No'], [1, '30000413']]
				}, # Review
			5: {
				'duration': 24,
				'criteria': [[5, 'No'], [4, 'No']]
				}, # Appeal
			6: {
				'duration': 12,
				'criteria': [[5, 'No']]
				} # Appeal Response
		}
	},
	'design': {
		'name': 'Design',
		'project_category_id': 17, # Web Design
		'project_phases': {
			1: {
				'duration': 48, # in hour
				'criteria': [[2, '0'], [5, 'No']]
				}, # Register
			2: {
				'duration': 48,
				'criteria': [[3, '0'], [5, 'No']]
				}, # Submission
			3: {
				'duration': 12,
				'criteria': [[1, '30000410']]
				}, # Screening
			4: {
				'duration': 24,
				'criteria': [[5, 'No'], [1, '30000411']]
				}, # Review
			11: {
				'duration': 12,
				'criteria': [[1, '30000720']]
				}, # Approval
		}
	},
	'design-2round': {
		'name': 'Design 2 Round',
		'project_category_id': 17, # Web Design
		'project_phases': {
			13: {
				'duration': 0, # in hour
				'criteria': [[3, '0'], [5, 'No']]
				}, # Specification Submission
			14: {
				'duration': 1,
				'criteria': [[1, '30000722'], [6, 3]]
				}, # Specification Review
			1: {
				'duration': 48, # in hour
				'criteria': [[2, '0'], [5, 'No']]
				}, # Register
			15: {
				'duration': 48,
				'criteria': [[3, '0'], [5, 'No']]
				}, # Checkpoint Submission
			16: {
				'duration': 6,
				'criteria': [[1, '30000416']]
				}, # Checkpoint Screening
			17: {
				'duration': 24,
				'criteria': [[1, '30000417'], [6, 3]]
				}, # Checkpoint Review
			2: {
				'duration': 96,
				'criteria': [[3, '0'], [5, 'No']]
				}, # Submission
			3: {
				'duration': 12,
				'criteria': [[1, '30000410']]
				}, # Screening
			4: {
				'duration': 12,
				'criteria': [[5, 'No'], [1, '30000411'], [6, 3]]
				}, # Review
			9: {
				'duration': 24,
				'criteria': [[5, 'No']]
				}, # Final Fix
			10: {
				'duration': 6,
				'criteria': [[5, 'No']]
				}, # Final Review
			11: {
				'duration': 12,
				'criteria': [[1, '30000720']]
				}, # Approval
		}
	},
	'qa': {
		'name': 'QA',
		'project_category_id': 9, # Bug Hunt
		'project_phases': {
			1: {
				'duration': 48, # in hour
				'criteria': [[2, '0'], [5, 'No']]
				}, # Register
			2: {
				'duration': 48,
				'criteria': [[3, '0'], [5, 'No']]
				}, # Submission
			4: {
				'duration': 24,
				'criteria': [[5, 'No'], [1, '30000413']]
				}, # Review
		}
	},
	'mm': {
		'name': 'Data Science',
		'project_category_id': 37, # Marathon Match
		'project_phases': {
			1: {
				'duration': 48, # in hour
				'criteria': [[2, '0'], [5, 'No']]
				}, # Register
			2: {
				'duration': 48,
				'criteria': [[3, '0'], [5, 'No']]
				}, # Submission
			4: {
				'duration': 24,
				'criteria': [[5, 'No'], [1, '30000413']]
				},
		}
	},
	'f2f': {
		'name': 'First2Finish',
		'project_category_id': 38, # First2Finish
		'project_phases': {
			1: {
				'duration': 24*30, # in hour
				'criteria': [[2, '0'], [5, 'No']]
				}, # Register
			2: {
				'duration': 24*30,
				'criteria': [[3, '0'], [5, 'No']]
				}, # Submission
			18: {
				'duration': 24,
				'criteria': [[1, '30000419']]
				}, # Iterative Review
		}
	},
	'f2fd': {
		'name': 'First2Finish Design',
		'project_category_id': 40, # Design First2Finish
		'project_phases': {
			1: {
				'duration': 24*30, # in hour
				'criteria': [[2, '0'], [5, 'No']]
				}, # Register
			2: {
				'duration': 24*30,
				'criteria': [[3, '0'], [5, 'No']]
				}, # Submission
			18: {
				'duration': 24,
				'criteria': [[1, '30000419']]
				},
		}
	}
}

user_id = 132456
user_name = 'dok'

project_status_id = 1 	# Status: Active

print("database tcs_catalog;")

for x in range(total):
	random_hours = random.randint(0, 168) # Random time from now to 168 hours (7 days) ago to cover various active phase
	real_now = datetime.now().replace(microsecond=0) # real time of now
	current = real_now - timedelta(hours=random_hours) # time of project created

	project_id = init_project_id + x

	select_type = random.choice(list(project_details.values())) # choice track/catogery randomly
	project_name = select_type['name']
	project_category_id = select_type['project_category_id']
	project_phases = select_type['project_phases']

	print("-- Data for project")
	project_query = """INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date)
	VALUES ('{project_id}', '{project_status_id}', '{project_category_id}', '{user_id}', '{current}', '{user_id}', '{current}');""".format(
		project_id=project_id,
		project_status_id=project_status_id,
		project_category_id=project_category_id,
		user_id=user_id,
		current=current
	)
	print(project_query)


	print("-- Data for project_info")
	project_info = {
		3: 1, # Version
		4: 0, # Forum ID
		6: f'{project_name} Challenge #{project_id}', # Project Name
		7: 1.0, # Project Version
		9: 'On', # Autopilot option
		10: 'On', # Status Notification
		11: 'On', # Timeline Notification
		12: 'Yes', # Public
		13: 'Yes', # Rated
		14: 'Open', # Eligibility
		16: 0.0, # Payments
		26: 'On', # Digital Run Flag
		29: 'On', # Contest Indicator
		42: 'true', # Requires Other Fixes
		43: 'true' # Send Winner Emails
	}
	for project_info_type_id, value in project_info.items():
		project_info_query = """INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('{project_id}', '{project_info_type_id}', '{value}', '{user_id}', '{current}', '{user_id}', '{current}');""".format(
		project_id=project_id,
		project_info_type_id=project_info_type_id,
		value=value,
		user_id=user_id,
		current=current
		)
		print(project_info_query)


	print("--  Data for project_phase")
	first_phase = True
	for phase_type_id, v in project_phases.items():
		duration_hours = v['duration']
		delta = timedelta(hours=duration_hours)

		if first_phase:
			start_time = current
			end_time = current
			first_phase = False

		if phase_type_id == 1: # Registration
			registration_time = end_time

		if phase_type_id == 2 or phase_type_id == 15: # Submission & Checkpoint Submission
			start_time = registration_time
			end_time = start_time + delta
		elif phase_type_id == 18 and (project_category_id == 38 or project_category_id == 40): # First2Finish Iterative Review
			start_time = registration_time + delta
			end_time = start_time + delta
		else:
			start_time = end_time
			end_time = start_time + delta

		diff_ms = (end_time - start_time).total_seconds() * 1000

		if start_time <= real_now < end_time:
			phase_status_id = 2 # Open
		elif real_now >= end_time:
			phase_status_id = 3 # Closed
		else:
			phase_status_id = 1 # Scheduled

		project_phase_query = """INSERT INTO project_phase (project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date)
	VALUES ('{project_id}{phase_type_id}', '{project_id}', '{phase_type_id}', '{phase_status_id}', '{start_time}', '{start_time}', '{end_time}', NULL, NULL, '{diff_ms}', '{user_id}', '{current}', '{user_id}', '{current}');""".format(
			project_id=project_id,
			phase_type_id=phase_type_id,
			phase_status_id=phase_status_id,
			start_time=start_time,
			end_time=end_time,
			diff_ms=diff_ms,
			user_id=user_id,
			current=current
			)
		print(project_phase_query)


	print("--  Data for phase_criteria")
	for phase_type_id, v in project_phases.items():
		criterias = v['criteria']
		for i in range(len(criterias)):
			phase_criteria_type_id, parameter = criterias[i]
			project_phase_id = str(project_id) + str(phase_type_id)
			phase_criteria_query = """INSERT INTO phase_criteria (project_phase_id, phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date)
	VALUES ('{project_phase_id}', '{phase_criteria_type_id}', '{parameter}', '{user_id}', '{current}', '{user_id}', '{current}');""".format(
			project_phase_id=project_phase_id,
			phase_criteria_type_id=phase_criteria_type_id,
			parameter=parameter,
			user_id=user_id,
			current=current
			)
			print(phase_criteria_query)


	print("--  Data for phase_dependency")
	phase = list(project_phases)
	for i in range(len(phase)):
		if i == len(phase)-1:
			break
		dependency_start = 0
		if i == 0:
			dependency_start = 1
		dependency_phase_id = str(project_id) + str(phase[i])
		dependent_phase_id = str(project_id) + str(phase[i+1])
		phase_dependency_query = """INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
	VALUES ('{dependency_phase_id}', '{dependent_phase_id}', '{dependency_start}', '1', '0', '{user_id}', '{current}', '{user_id}', '{current}');""".format(
			dependency_phase_id=dependency_phase_id,
			dependent_phase_id=dependent_phase_id,
			dependency_start=dependency_start,
			user_id=user_id,
			current=current
			)
		print(phase_dependency_query)


	print("--  Data for resource")
	resource_query = """INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date, modify_user, modify_date)
	VALUES ('{project_id}', '13', '{project_id}', NULL, {user_id}, '{user_id}', '{current}', '{user_id}', '{current}');""".format(
		project_id=project_id,
		user_id=user_id,
		current=current
		)
	print(resource_query)


	print("--  Data for resource_info")
	resource_info = {
		1: user_id,
		2: user_name
	}
	for resource_info_type_id, value in resource_info.items():
		resource_info_query = """INSERT INTO resource_info (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)
	VALUES ('{project_id}', '{resource_info_type_id}', '{value}', '{user_id}', '{current}', '{user_id}', '{current}');""".format(
			project_id=project_id,
			resource_info_type_id=resource_info_type_id,
			value=value,
			user_id=user_id,
			current=current
			)
		print(resource_info_query)

	print()
