[Project] on OpenShift
=========================

Description du projet


Running on OpenShift
--------------------

Create an account at http://openshift.redhat.com/

Create a PHP application

	rhc-create-app -a [Project] -t php-5.3 -l $USERNAME

Add mysql support to your application
    
	rhc-ctl-app -a [Project] -e add-mysql-5.1 -l $USERNAME
Make a note of the username, password, and host name as you will need to use these to complete the [Project] installation on OpenShift

Add this upstream [Project] quickstart repo

	cd [Project]/php
	rm -rf *
	git remote add upstream -m master git://github.com/gshipley/[Project]-openshift-quickstart.git
	git pull -s recursive -X theirs upstream master

Then push the repo upstream to OpenShift

	git push

That's it, you can now checkout your application at:

	http://[Project]-$yourlogin.rhcloud.com