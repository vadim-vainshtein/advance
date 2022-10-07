package test.highmax.finance.screen.client;

import io.jmix.ui.screen.*;
import test.highmax.finance.entity.Client;

@UiController("Client.browse")
@UiDescriptor("client-browse.xml")
@LookupComponent("clientsTable")
public class ClientBrowse extends StandardLookup<Client> {
}