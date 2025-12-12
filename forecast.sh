case "$1" in
	book)
		cd /Users/whizmate/Work/workspace/Forecast
		;;
	odroid)
		cd /home/odroid/Work/Forecast
		;;
	nuc)
		cd /home/pony/Work/git/forecast
		;;
	pi)
		cd /home/pi/Forecast
		;;
	*)
	echo "Usage: ./run.sh {book|odroid|nuc|pi}"
		exit 1
    ;;
esac

#mvn clean install
java -jar target/forecast-2.0.jar
