import React from 'react';
import PropTypes from 'prop-types';
import { Input, Button, Checkbox, Popup, Grid } from 'semantic-ui-react';
import { compose, withState } from 'recompose';
import { connect } from 'react-redux';

import {
  ExpressionBuilder,
  actionCreators as expressionBuilderActionCreators,
} from 'components/ExpressionBuilder';

import { processSearchString } from './searchBarUtils';

const { expressionChanged } = expressionBuilderActionCreators;

const withIsExpression = withState('isExpression', 'setIsExpression', false);

const withSearchString = withState('searchString', 'setSearchString', '');
const withExpression = withState('expression', 'setExpression', '');

const withIsSearchStringValid = withState('isSearchStringValid', 'setIsSearchStringValid', true);
const withSearchStringValidationMessage = withState(
  'SearchStringValidationMessage',
  'setSearchStringValidationMessage',
  undefined,
);

const enhance = compose(
  connect(
    (state, props) => ({
      dataSource: state.expressionBuilder.dataSources[props.expressionDataSourceUuid],
    }),
    { expressionChanged },
  ),
  withIsExpression,
  withSearchString,
  withExpression,
  withIsSearchStringValid,
  withSearchStringValidationMessage,
);

const SearchBar = ({
  expressionDataSourceUuid,
  dataSource,
  expressionId,
  expressionChanged,
  searchString,
  isExpression,
  setIsExpression,
  setSearchString,
  expression,
  setExpression,
  setIsSearchStringValid,
  isSearchStringValid,
  setSearchStringValidationMessage,
  SearchStringValidationMessage,
}) => {
  const searchInput = (
    <React.Fragment>
      <Popup
        trigger={
          <Button
            circular
            icon="edit"
            onClick={() => {
              const parsedExpression = processSearchString(dataSource, searchString);
              expressionChanged(expressionId, parsedExpression.expression);

              setIsExpression(true);
            }}
          />
        }
        content="Switch to using the expression builder. You won't be able to convert back to a text search and keep your expression."
      />
      <Input
        value={searchString}
        error={!isSearchStringValid}
        className="SearchBar__input"
        onChange={(_, data) => {
          const expression = processSearchString(dataSource, data.value);
          const invalidFields = expression.fields.filter(field => !field.conditionIsValid || !field.fieldIsValid || !field.valueIsValid);
          setIsSearchStringValid(invalidFields.length === 0);
          setSearchStringValidationMessage(invalidFields.length === 0 ? undefined : 'TODO: bad');
          setSearchString(data.value);
        }}
      />
      <Button>Search</Button>
    </React.Fragment>
  );

  const expressionBuilder = (
    <React.Fragment>
      <Grid>
        <Grid.Column width={1}>
          <Popup
            trigger={<Button circular icon="text cursor" onClick={() => setIsExpression(false)} />}
            content="Switch to using text search. You'll lose the expression you've built here."
          />
        </Grid.Column>
        <Grid.Column width={15}>
          <ExpressionBuilder
            showModeToggle={false}
            editMode
            dataSourceUuid={expressionDataSourceUuid}
            expressionId={expressionId}
          />
        </Grid.Column>
      </Grid>
    </React.Fragment>
  );

  return <div>{isExpression ? expressionBuilder : searchInput}</div>;
};

SearchBar.propTypes = {
  expressionDataSourceUuid: PropTypes.string.isRequired,
  expressionId: PropTypes.string.isRequired,
  searchString: PropTypes.string,
};

export default enhance(SearchBar);
