/*
 * Copyright 2018 Crown Copyright
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import React from 'react';
import { compose, lifecycle } from 'recompose';
import { connect } from 'react-redux';
import { withRouter } from 'react-router-dom';
import ReactDOM from 'react-dom';
import { Input, Button, Header, Icon, Modal, Menu } from 'semantic-ui-react';

import Mousetrap from 'mousetrap';

import { actionCreators as recentItemsActionCreators } from './redux';
import openDocRef from './openDocRef';

const {
  recentItemsClosed,
  recentItemsSelectionUp,
  recentItemsSelectionDown,
} = recentItemsActionCreators;

const enhance = compose(
  withRouter,
  connect(
    (state, props) => ({
      isOpen: state.recentItems.isOpen,
      openItemStack: state.recentItems.openItemStack,
      topItem: state.recentItems.openItemStack,
      selectedItem: state.recentItems.selectedItem,
      selectedDocRef: state.recentItems.selectedDocRef,
    }),
    {
      recentItemsClosed,
      openDocRef,
      recentItemsSelectionUp,
      recentItemsSelectionDown,
    },
  ),
  lifecycle({
    componentDidMount() {
      const {
        recentItemsSelectionUp,
        recentItemsSelectionDown,
        openDocRef,
        recentItemsClosed,
        selectedDocRef,
        history,
        openItemStack,
      } = this.props;

      Mousetrap.bind(['k', 'up'], () => recentItemsSelectionUp());
      Mousetrap.bind(['j', 'down'], () => recentItemsSelectionDown());
      Mousetrap.bind('enter', () => {
        if (selectedDocRef !== undefined) {
          openDocRef(history, selectedDocRef);
          recentItemsClosed();
        } else if (openItemStack.length > 0) {
          openDocRef(history, openItemStack[0]);
          recentItemsClosed();
        }
      });
    },
    componentWillUnmount() {
      Mousetrap.unbind(['k', 'up']);
      Mousetrap.unbind(['j', 'down']);
      Mousetrap.unbind('enter');
    },
  }),
);

const RecentItemsContent = ({
  history,
  isOpen,
  recentItemsClosed,
  openItemStack,
  openDocRef,
  selectedItem,
  selectedDocRef,
  recentItemsSelectionUp,
  recentItemsSelectionDown,
}) => (
  <Menu vertical fluid>
    <div>
      {openItemStack.map((docRef, i) => {
        const title = docRef.name;
        return (
          <Menu.Item
            active={selectedItem === i}
            key={docRef.uuid}
            name={title}
            onClick={() => {
              openDocRef(history, docRef);
              recentItemsClosed();
            }}
          >
            {title}
          </Menu.Item>
        );
      })}
    </div>
  </Menu>
);
export default enhance(RecentItemsContent);