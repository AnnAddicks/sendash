/*
 * Created by SharpDevelop.
 * User: brian
 * Date: 7/31/2015
 * Time: 6:29 PM
 * 
 * To change this template use Tools | Options | Coding | Edit Standard Headers.
 */
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Diagnostics;
using System.ServiceProcess;
using System.Text;
using System.Net;
using System.Net.Security;
using System.Security.Cryptography.X509Certificates;
using NCrontab;
using Microsoft.Win32;


namespace Dropship {
	public class Dropship : ServiceBase {
		public const string MyServiceName = "Dropship";
		//public const striyoung SendashUrl = "https://sendash.com/status/stub";
		//public string SendashUrl = "";
		//public const string ApiKey = "123456";
		//public const string CronTabString = "*/2 * * * *";
		
		public static string ApiKey = "";
		public static string CronTabString = "";
		public static string SendashUrl = "";

		
		private static void GetConfigFromRegistry() {
			// Get stored values
			RegistryKey rk = Registry.LocalMachine.OpenSubKey("Software\\Sendash\\Dropship", false);
			SendashUrl = (rk.GetValue("SendashUrl") as string);
			ApiKey = (rk.GetValue("ApiKey") as string);
			CronTabString = (rk.GetValue("CronTabString") as string);
		}
		
		// Function for writing to custom even log for service
		private static void LogEvent(string message) {
			string eventSource = "Dropship";
			DateTime dt = new DateTime();
			dt = System.DateTime.UtcNow;
			message = dt.ToLocalTime() + ": " + message;
		
			EventLog.WriteEntry(eventSource, message);
		}

		
		// Function for performing Httpquery
		public static string WRequest(string URL, string method, string postData) {
			string responseData = "";
			try {
				ServicePointManager.SecurityProtocol = SecurityProtocolType.Tls12;
				System.Net.CookieContainer cookieJar = new System.Net.CookieContainer();
				System.Net.HttpWebRequest hwrequest = (System.Net.HttpWebRequest)System.Net.WebRequest.Create(URL);
				hwrequest.CookieContainer = cookieJar;
				hwrequest.Accept = "*/*";
				hwrequest.AllowAutoRedirect = true;
				hwrequest.UserAgent = "http_requester/0.1";
				hwrequest.Timeout = 60000;
				hwrequest.Method = method;
				hwrequest.ContentType = "application/json";
				hwrequest.Headers["API_KEY"] = ApiKey;
				if (hwrequest.Method == "POST") {
					hwrequest.ContentType = "application/x-www-form-urlencoded";
					// Use UTF8Encoding instead of ASCIIEncoding for XML requests:
					System.Text.ASCIIEncoding encoding = new System.Text.ASCIIEncoding();
					byte[] postByteArray = encoding.GetBytes(postData);
					hwrequest.ContentLength = postByteArray.Length;
					System.IO.Stream postStream = hwrequest.GetRequestStream();
					postStream.Write(postByteArray, 0, postByteArray.Length);
					postStream.Close();
				}
				System.Net.HttpWebResponse hwresponse = (System.Net.HttpWebResponse)hwrequest.GetResponse();
				if (hwresponse.StatusCode == System.Net.HttpStatusCode.OK) {
					System.IO.Stream responseStream = hwresponse.GetResponseStream();
					System.IO.StreamReader myStreamReader =	new System.IO.StreamReader(responseStream);
					responseData = myStreamReader.ReadToEnd();
				}
				hwresponse.Close();
			} catch (Exception e) {
				responseData = "An error occurred (" + URL + "): " + e.Message;
			}
			return responseData;
		}
		
		// stuff that happens when the time is right
		public void OnTimer(object sender, System.Timers.ElapsedEventArgs args) {
			var Schedule = CrontabSchedule.Parse(CronTabString);
			var NextRun = Schedule.GetNextOccurrence(DateTime.Now);

			if ((NextRun > DateTime.Now) && (NextRun < DateTime.Now.AddMinutes(1))) {
				LogEvent((WRequest(SendashUrl, "GET", "")));
			}
		}
		
		public Dropship() {
			InitializeComponent();
		}
		
		private void InitializeComponent() {
			this.ServiceName = MyServiceName;
		}
		
		/// <summary>
		/// Clean up any resources being used.
		/// </summary>
		protected override void Dispose(bool disposing) {
			// TODO: Add cleanup code here (if required)
			base.Dispose(disposing);
		}
		
		/// <summary>
		/// Start this service.
		/// </summary>
		protected override void OnStart(string[] args) {
			
			//LogEvent((WRequest(SendashUrl, "GET", "")));
			
			LogEvent("start dat service");
			
			// Get stored values
			RegistryKey rk = Registry.LocalMachine.OpenSubKey("Software\\Sendash\\Dropship", false);
			//public string SendashUrl = rk.GetValue("SendashUrl");
			string SendashUrl = (string) rk.GetValue("SendashUrl");
			//public string SendashUrl = rk.GetValue("SendashUrl").ToString();
			
			// Create a timer and set a two second interval.
			var aTimer = new System.Timers.Timer(10000);

			// Hook up the Elapsed event for the timer. 
			aTimer.Elapsed += OnTimer;
	
			// Have the timer fire repeated events (true is the default)
			aTimer.AutoReset = true;
	
			// Start the timer
			aTimer.Enabled = true;
		}
		
		/// <summary>
		/// Stop this service.
		/// </summary>
		protected override void OnStop() {
			// TODO: Add tear-down code here (if required) to stop your service.
		}
	}
}
