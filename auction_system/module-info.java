module Auction {
	exports application.auction;
	exports application.art;
	exports application;
	exports application.controllers;
	exports application.tools;
	exports application.gui;
	exports application.payment;
	exports application.observer;
	exports application.managers;
	exports application.exceptions;
	exports application.users;

	requires transitive javafx.base;
	requires transitive javafx.controls;
	requires transitive javafx.graphics;
}