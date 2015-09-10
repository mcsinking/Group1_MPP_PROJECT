/*
 * Copyright (c) 2011, 2012 Oracle and/or its affiliates.
 * All rights reserved. Use is subject to license terms.
 *
 * This file is available and licensed under the following license:
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  - Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *  - Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the distribution.
 *  - Neither the name of Oracle nor the names of its
 *    contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.mum.library.controller;

import com.mum.library.business.Address;
import com.mum.library.business.LibraryMember;
import com.mum.library.dataaccess.DataAccessFacade;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class AddLibraryMemberController {

	@FXML
	private TextField memberaid;

	@FXML
	private TextField lastnameaid;

	@FXML
	private TextField cellnoaid;

	@FXML
	private TextField fnameid;

	@FXML
	private TextField addressaid;

	@FXML
	private TextField cityid;

	@FXML
	private TextField zipcodeaid;

	@FXML
	private TextField stateaid;

	@FXML
	protected void handleSubmitButtonAction(ActionEvent event) {

		Address address = new Address(addressaid.getText(), cityid.getText(), stateaid.getText(), zipcodeaid.getText());
		LibraryMember libraryMember = new LibraryMember(fnameid.getText(), lastnameaid.getText(), cellnoaid.getText(),
				address, memberaid.getText());
		DataAccessFacade.saveToStorage(DataAccessFacade.StorageType.MEMBERS, (Object) libraryMember);
		clearMemberData();
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Added SuccessFull");
		alert.setHeaderText(null);
		alert.setContentText("Library Member has been added SuccessFully");
		alert.showAndWait();
	}
	public void clearMemberData(){
		fnameid.clear();
		lastnameaid.clear();
		memberaid.clear();
		addressaid.clear();
		cityid.clear();
		stateaid.clear();
		zipcodeaid.clear();
		cellnoaid.clear();
	}
}
